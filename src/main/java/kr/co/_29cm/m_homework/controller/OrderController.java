package kr.co._29cm.m_homework.controller;

import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    public Order createOrder() {
        return orderService.createOrder();
    }

    public void payForOrder(Order order) {
        orderService.payForOrder(order);
    }
}
