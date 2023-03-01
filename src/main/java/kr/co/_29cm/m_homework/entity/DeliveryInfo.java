package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 * 배달 정보 엔티티
 * 배달 정보 데이터가 메모리 저장소에 저장되는 형태
 * </pre>
 */
@Getter
@Builder
public class DeliveryInfo implements BaseEntity {
    // 배달 종류 (general / special)
    private String id;

    // 배달비
    private int charge;

    // 배달비 면제받기 위한 주문 금액
    private int chargeFreePrice;
}
