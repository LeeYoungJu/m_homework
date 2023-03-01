package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.entity.BaseEntity;

import java.util.List;

/**
 * Data Storage에 접근할 수 있는 함수 형태를 정의한 인터페이스
 * 이 인터페이스를 원하는 방식으로 구현해서 Service 레이어에서 사용할 수 있다.
 */
public interface BaseRepository {
    /**
     * Data Storage에 저장된 특정 토픽의 컬럼 이름 리스트 반환
     * @param targetClass 토픽 클래스
     * @return 컬럼 이름 리스트
     */
    List<String> selectColNames(Class<?> targetClass);

    /**
     * Data Storage에 저장된 특정 토픽의 전체 데이터 리스트 반환
     * @param targetClass 토픽 클래스
     * @return 전체 데이터 리스트
     */
    <T> List<T> selectAll(Class<T> targetClass);

    /**
     * Data Storage에 저장된 특정 토픽의 특정 데이터 하나를 id를 이용해 검색 후 반환
     * @param targetClass 토픽 클래스
     * @param id 검색할 데이터 id
     * @return 검색된 데이터
     */
    <T> T selectOneById(Class<T> targetClass, String id);

    /**
     * Data Storage에 저장된 특정 토픽의 복수의 데이터를 ids(id리스트)를 이용해 검색 후 반환
     * @param targetClass 토픽 클래스
     * @param ids 검색할 데이터 id 리스트
     * @return 검색된 데이터 리스트
     */
    <T> List<T> selectByIds(Class<T> targetClass, List<String> ids);

    /**
     * 특정 토픽의 수정된 데이터 리스트(newList)를 넘겨주면 Data Storage에 update 된다.
     * @param targetClass 토픽 클래스
     * @param newList 업데이트할 데이터 리스트
     */
    <T> void updateCommit(Class<T> targetClass, List<BaseEntity> newList);
}
