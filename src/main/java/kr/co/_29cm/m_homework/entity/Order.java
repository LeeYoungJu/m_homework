package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Order {
    private String id;
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    public void addProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
    }

    public boolean isProductEmpty() {
        return orderProducts.size() == 0;
    }
}
