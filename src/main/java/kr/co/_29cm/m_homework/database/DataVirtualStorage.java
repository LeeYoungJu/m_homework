package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.config.PropertyManager;
import kr.co._29cm.m_homework.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataVirtualStorage {
    /*
     * 메모리 데이터 저장소
     */
    public static Map<String, List<String>> tableColumnMap = new HashMap<>();
    public static Map<String, List<BaseEntity>> tableDataMap = new HashMap<>();


    private final PropertyManager propertyManager;
    private final DataLoaderFactory dataLoaderFactory;

    private List<String> topicList;
    private String dataFileType;

    public void initData() {
        dataFileType = propertyManager.getProperty("data.file.ext");
        topicList = Arrays.asList(propertyManager.getProperty("data.topic.list").split(","));

        System.out.println("데이터 로딩 중...");

        DataLoader loader = dataLoaderFactory.getLoader(dataFileType);
        loader.loadDataOnMemory(topicList
                , (topic, colNames) -> tableColumnMap.put(topic, colNames)
                , (topic, dataList) -> tableDataMap.put(topic, dataList));
    }

    public List<String> geColNames(Class<?> targetClass) {
        return tableColumnMap.get(targetClass.getSimpleName());
    }

    public List<BaseEntity> getAllData(Class<?> targetClass) {
        return tableDataMap.get(targetClass.getSimpleName());
    }
}
