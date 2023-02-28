package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryInfo implements BaseEntity {
    private String id;
    private int charge;
    private int chargeFreePrice;
}
