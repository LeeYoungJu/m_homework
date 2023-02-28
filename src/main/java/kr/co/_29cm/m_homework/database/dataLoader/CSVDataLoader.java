package kr.co._29cm.m_homework.database.dataLoader;

import kr.co._29cm.m_homework.config.PropertyManager;
import kr.co._29cm.m_homework.database.DataListBuilder;
import kr.co._29cm.m_homework.database.DataListBuilderFactory;
import kr.co._29cm.m_homework.database.DataLoader;
import kr.co._29cm.m_homework.database.DataVirtualStorage;
import kr.co._29cm.m_homework.database.consts.DataFileExt;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.service.reader.BaseFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CSVDataLoader implements DataLoader {
    private final BaseFileReader baseFileReader;
    private final DataListBuilderFactory dataListBuilderFactory;
    private final DataVirtualStorage dataVirtualStorage;
    private final PropertyManager propertyManager;

    private String fileExt = DataFileExt.CSV;

    @Override
    public String getFileExt() {
        return fileExt;
    }

    @Override
    public void loadDataOnMemory() {
        List<String> topicList =
                Arrays.asList(propertyManager.getProperty("data.topic.list").split(","));

        topicList.forEach(topic -> {
            String fileName = topic.trim() + "." + fileExt;
            List<String[]> rows = baseFileReader.readFile(fileName);
            List<String> colNames = Arrays.stream(rows.get(0)).collect(Collectors.toList());

            DataListBuilder dataListBuilder = dataListBuilderFactory.getBuilder(topic);
            List<BaseEntity> dataList = dataListBuilder.buildList(rows);

            dataVirtualStorage.setColumnMap(topic, colNames);
            dataVirtualStorage.setDataMap(topic, dataList);
        });
    }
}
