package kr.co._29cm.m_homework.controller;

import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.exception.SoldOutException;
import kr.co._29cm.m_homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 사용자 화면과 OrderService 기능을 중간에서 매핑해주는 Controller 클래스
 */
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
