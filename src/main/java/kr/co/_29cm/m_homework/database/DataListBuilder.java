package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.entity.BaseEntity;

import java.util.List;

public interface DataListBuilder {

    String getTopic();
    List<BaseEntity> buildList(List<String[]> rows);
}
