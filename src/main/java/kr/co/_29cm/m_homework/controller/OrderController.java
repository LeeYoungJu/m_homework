package kr.co._29cm.m_homework.controller;

import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.exception.SoldOutException;
import kr.co._29cm.m_homework.service.DeliveryInfoService;
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

    public void payFor(Order order) throws SoldOutException {
        orderService.payFor(order);
    }
}
