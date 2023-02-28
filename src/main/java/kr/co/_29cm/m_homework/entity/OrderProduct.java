package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProduct {

    private String productId;
    private int orderAmt;
    private Product product;

    public void addOrderAmt(int amt) {
        orderAmt += amt;
    }

    public int getTotalPrice() {
        return orderAmt * product.getPrice();
    }
}
