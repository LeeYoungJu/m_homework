package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.entity.*;
import kr.co._29cm.m_homework.exception.SoldOutException;
import kr.co._29cm.m_homework.repository.DataRepository;
import kr.co._29cm.m_homework.service.consts.DeliveryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderService {
    private final DataRepository dataRepository;
    private final DeliveryInfoService deliveryInfoService;

    public Order createOrder() {
        DeliveryInfo deliveryInfo = deliveryInfoService.getDeliveryCharge(DeliveryType.GENERAL);
        return Order.builder()
                .id(UUID.randomUUID().toString())
                .orderProducts(new ArrayList<>())
                .deliveryInfo(deliveryInfo)
                .build();
    }

    public void payFor(Order order) throws SoldOutException {
        List<OrderProduct> orderProducts = order.getOrderProducts();
        List<String> productIds = orderProducts
                .stream()
                .map(OrderProduct::getProductId)
                .collect(Collectors.toList());

        List<Product> proxyProducts = dataRepository.selectByIds(Product.class, productIds);
        List<BaseEntity> newProducts = new ArrayList<>();

        ListIterator<Product> iterator = proxyProducts.listIterator();
        while(iterator.hasNext()) {
            int idx = iterator.nextIndex();
            newProducts.add(
                proxyProducts.get(idx)
                        .decreaseStockAmt(orderProducts.get(idx).getOrderAmt())
            );
            iterator.next();
        }

        dataRepository.updateData(Product.class, newProducts);
    }
}
