package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataVirtualStorage {
    /*
     * 메모리 데이터 저장소
     */
    private static Map<String, List<String>> tableColumnMap = new HashMap<>();
    private static Map<String, List<BaseEntity>> tableDataMap = new HashMap<>();


    public void setColumnMap(String topic, List<String> colNames) {
        tableColumnMap.put(topic, colNames);
    }
    public void setDataMap(String topic, List<BaseEntity> data) {
        tableDataMap.put(topic, data);
    }

    public List<String> geColNames(Class<?> targetClass) {
        return tableColumnMap.get(targetClass.getSimpleName());
    }

    public List<BaseEntity> getAllData(Class<?> targetClass) {
        return tableDataMap.get(targetClass.getSimpleName());
    }

    public <T> void updateData(Class<T> targetClass, List<BaseEntity> newData) {
        tableDataMap.put(targetClass.getSimpleName(), newData);
    }
}
