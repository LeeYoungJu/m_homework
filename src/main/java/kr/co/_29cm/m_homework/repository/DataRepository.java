package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.database.DataVirtualStorage;
import kr.co._29cm.m_homework.database.util.EntityBinarySearch;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 책임 : 메모리 데이터 조회/수정 클래스
 * 메모리 저장소에 저장되어 있는 엔티티들을 다루는 함수들을 구현한 클래스다.
 * 데이터 토픽의 종류와 상관없이 사용할 수 있다.
 * 컬럼명 리스트 조회, 전체 데이터 조회, 특정 데이터 조회 등을 구현한다.
 * </pre>
 */
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
    public <T> T selectOneById(Class<T> targetClass, String id) throws EntityNotFoundException {
        List<BaseEntity> allData = dataVirtualStorage.getAllData(targetClass);
        EntityBinarySearch entityBinarySearch = new EntityBinarySearch(allData);
        int idx = entityBinarySearch.binarySearchOneTarget(id, 0, allData.size() - 1);

        return (T) allData.get(idx);

    }

    @Override
    public <T> List<T> selectByIds(Class<T> targetClass, List<String> ids) throws EntityNotFoundException {
        List<BaseEntity> allData = dataVirtualStorage.getAllData(targetClass);

        EntityBinarySearch entityBinarySearch = new EntityBinarySearch(allData);
        List<Integer> indexes = entityBinarySearch.binarySearchMultiTarget(ids);

        return indexes.stream()
                .map(idx -> (T) allData.get(idx)).collect(Collectors.toList());
    }

    @Override
    public <T> void updateCommit(Class<T> targetClass, List<BaseEntity> newList) {
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
