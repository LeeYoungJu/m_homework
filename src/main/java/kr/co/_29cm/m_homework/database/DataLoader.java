package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.entity.BaseEntity;

import java.util.List;
import java.util.function.BiConsumer;

public interface DataLoader {
    String getFileExt();
    void loadDataOnMemory(List<String> topicList
            , BiConsumer<String, List<String>> setColumn
            , BiConsumer<String, List<BaseEntity>> setData);
}
