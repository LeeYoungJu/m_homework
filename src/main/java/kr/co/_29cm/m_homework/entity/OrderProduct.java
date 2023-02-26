package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProduct {

    private String ProductId;
    private int orderAmt;
}
