package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class OrderService {
    public Order createOrder() {
        return Order.builder()
                .id(UUID.randomUUID().toString())
                .orderProducts(new ArrayList<>())
                .build();
    }

    public void payForOrder(Order order) {

    }
}
