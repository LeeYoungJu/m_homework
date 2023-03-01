package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 데이터 메모리 저장소를 래핑하고 있는 클래스
 * 파일에 저장된 원본 데이터가 DataLoader에 의해서 이 클래스의 클래스변수에 저장된다.
 * </pre>
 */
@Component
@RequiredArgsConstructor
public class DataVirtualStorage {

    /*
     * 데이터 메모리 저장소(클래스변수)
     * private으로 작성되어서 오로지 이 클래스에서 제공하는 함수로만 데이터를 직접적으로 다룰 수 있다.
     */
    private static Map<String, List<String>> tableColumnMap = new HashMap<>();
    private static Map<String, List<BaseEntity>> tableDataMap = new HashMap<>();


    /**
     * 메모리 저장소에 특정 토픽의 컬럼명 리스트를 저장한다.
     * @param topic 데이터 토픽명
     * @param colNames 저장할 컬럼명 리스트
     */
    public void setColumnMap(String topic, List<String> colNames) {
        tableColumnMap.put(topic, colNames);
    }

    /**
     * 메모리 저장소에 특정 토픽의 데이터 리스트를 저장한다.
     * @param topic 데이터 토픽명
     * @param data 저장할 데이터 리스트
     */
    public void setDataMap(String topic, List<BaseEntity> data) {
        tableDataMap.put(topic, data);
    }

    /**
     * 메모리 저장소에 저장된 특정 토픽의 컬럼명 리스트를 반환한다.
     * @param targetClass 토픽 클래스
     * @return 컬럼명 리스트
     */
    public List<String> geColNames(Class<?> targetClass) {
        return tableColumnMap.get(targetClass.getSimpleName());
    }

    /**
     * 메모리 저장소에 저장된 특정 토픽의 전체 데이터를 리스트 형태로 반환한다.
     * @param targetClass 토픽 클래스
     * @return 전체 데이터 리스트
     */
    public List<BaseEntity> getAllData(Class<?> targetClass) {
        return tableDataMap.get(targetClass.getSimpleName());
    }

    /**
     * 변경할 엔티티 데이터 리스트(newData)를 받아서 메모리 저장소에 업데이트 한다.
     * @param targetClass 토픽 클래스
     * @param newData 변경할 데이터 리스트
     */
    public <T> void updateData(Class<T> targetClass, List<BaseEntity> newData) {
        tableDataMap.put(targetClass.getSimpleName(), newData);
    }
}
