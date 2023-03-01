package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.entity.BaseEntity;

import java.util.List;

/**
 * <pre>
 * 해당 인터페이스를 구현하면
 * 특정 토픽의 데이터를 원본 파일에서 읽은 후 리스트 형태로 메모리에 올릴 때
 * 데이터 리스트가 메모리에 올라가는 형태를 결정해줄 수 있다.
 * 데이터 토픽이 추가되면 해당 인터페이스를 구현하는 클래스를 추가해야 한다.
 * </pre>
 */
public interface DataListBuilder {

    /**
     * 다루는 데이터 토픽을 반환한다.
     * @return
     */
    String getTopic();

    /**
     * 원본 파일에서 데이터를 읽어서 List<String[]> 형태로 받은 후
     * 메모리 데이터 저장소에 올라갈 형태를 결정한 후 리스트로 반환한다.
     * @param rows 파일(csv or ...)에서 읽은 행 리스트
     * @return 메모리 저장소에 올라갈 데이터 리스트
     */
    List<BaseEntity> buildList(List<String[]> rows);
}
