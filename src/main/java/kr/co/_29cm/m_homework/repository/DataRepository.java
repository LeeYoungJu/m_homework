package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.database.DataVirtualStorage;
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
        return dataVirtualStorage.geColNames(targetClass);
    }

    @Override
    public <T> List<T> selectAll(Class<T> targetClass) {
        return (List<T>) dataVirtualStorage.getAllData(targetClass);
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

        return (List<T>) allData.stream()
                .filter(data -> ids.contains(data.getId()))
                .collect(Collectors.toList());
    }
}
