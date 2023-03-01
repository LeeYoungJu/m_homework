package kr.co._29cm.m_homework.entity;

/**
 * <pre>
 * 데이터 저장소에 저장될 데이터들은 이 인터페이스를 구현해야 한다.
 * 모든 엔티티 구현체는 id 필드를 String 형태로 가지고 있어야 한다.
 * </pre>
 */
public interface BaseEntity {
    String getId();
}
