package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.entity.BaseEntity;

import java.util.List;

public interface BaseRepository {
    List<String> selectColNames(Class<?> targetClass);
    <T> List<T> selectAll(Class<T> targetClass);
    <T> T selectOneById(Class<T> targetClass, String id);
    <T> List<T> selectByIds(Class<T> targetClass, List<String> ids);
    <T> void updateData(Class<T> targetClass, List<BaseEntity> newList);
}
