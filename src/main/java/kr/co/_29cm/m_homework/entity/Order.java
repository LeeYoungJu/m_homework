package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Order {
    private String id;
    private List<OrderProduct> orderProducts;

    public void addProduct(OrderProduct orderProduct) {
        if(orderProducts.stream()
                .anyMatch(product -> product.getProductId().equals(orderProduct.getProductId()))
        ) {
            for(OrderProduct product : orderProducts) {
                if(product.getProductId().equals(orderProduct.getProductId())) {
                    product.addOrderAmt(orderProduct.getOrderAmt());
                    break;
                }
            }
        } else {
            orderProducts.add(orderProduct);
        }
    }

    public boolean isProductEmpty() {
        return orderProducts.size() == 0;
    }

}
