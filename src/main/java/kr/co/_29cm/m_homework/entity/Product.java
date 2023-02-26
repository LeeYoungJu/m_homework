package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
    private String id;
    private String name;
    private int price;
    private int stockAmt;

}
