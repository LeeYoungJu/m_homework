package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.database.DataVirtualStorage;
import kr.co._29cm.m_homework.database.util.EntityBinarySearch;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.exception.NoDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class DataRepository implements BaseRepository {
    private final DataVirtualStorage dataVirtualStorage;


    @Override
    public List<String> selectColNames(Class<?> targetClass) {
        return dataVirtualStorage.geColNames(targetClass)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public <T> List<T> selectAll(Class<T> targetClass) {
        return dataVirtualStorage.getAllData(targetClass)
                .stream()
                .map(data -> (T) data)
                .collect(Collectors.toList());
    }

    @Override
    public <T> T selectOneById(Class<T> targetClass, String id) throws NoDataException {
        List<BaseEntity> allData = dataVirtualStorage.getAllData(targetClass);
        Optional<BaseEntity> findOneOpt = allData.stream()
                .filter(data -> data.getId().equals(id)).findFirst();

        if(findOneOpt.isEmpty()) {
            throw new NoDataException();
        }

        return (T) findOneOpt.get();

    }

    @Override
    public <T> List<T> selectByIds(Class<T> targetClass, List<String> ids) {
        List<BaseEntity> allData = dataVirtualStorage.getAllData(targetClass);

        EntityBinarySearch entityBinarySearch = new EntityBinarySearch(allData);
        List<Integer> indexes = entityBinarySearch.binarySearchMultiTarget(allData, ids);

        return indexes.stream()
                .map(idx -> (T) allData.get(idx)).collect(Collectors.toList());
    }

    @Override
    public <T> void updateData(Class<T> targetClass, List<BaseEntity> newList) {
        List<BaseEntity> newDataList = dataVirtualStorage.getAllData(targetClass)
                .stream().map(data -> {
                    if(newList.stream()
                            .anyMatch(newData -> newData.getId().equals(data.getId()))) {
                        return newList.stream()
                                .filter(newData -> newData.getId().equals(data.getId()))
                                .findFirst().get();
                    }
                    return data;
                }).collect(Collectors.toList());

        dataVirtualStorage.updateData(targetClass, newDataList);
    }
}
