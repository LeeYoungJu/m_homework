package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * 주문 프로세스에서 상품이 추가될 때 데이터 형태
 */
@Getter
@Builder
public class OrderProduct {

    // 주문에 추가되는 상품
    private Product product;

    // 사용자가 주문한 상품 수량
    private int orderAmt;

    public void addOrderAmt(int amt) {
        orderAmt += amt;
    }

    public int getTotalPrice() {
        return orderAmt * product.getPrice();
    }
}
