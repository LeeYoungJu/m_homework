package kr.co._29cm.m_homework.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * <pre>
 * 사용자 주문을 표현하는 데이터 형태
 * 데이터 저장소에 저장될 필요는 없어서 BaseEntity를 구현하지는 않았다.
 * </pre>
 */
@Getter
@Builder
public class Order {
    private String id;
    private List<OrderProduct> orderProducts;
    private DeliveryInfo deliveryInfo;

    public void addProduct(OrderProduct orderProduct) {
        if(orderProducts.stream()
                .anyMatch(product -> product.getProduct().getId()
                        .equals(orderProduct.getProduct().getId()))
        ) {
            for(OrderProduct product : orderProducts) {
                if(product.getProduct().getId().equals(orderProduct.getProduct().getId())) {
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

    public boolean haveDeliveryCharge() {
        return getTotalOrderPrice() < deliveryInfo.getChargeFreePrice();
    }

    public int getTotalOrderPrice() {
        return orderProducts.stream().mapToInt(OrderProduct::getTotalPrice).sum();
    }
    public int getDeliveryCharge() {
        if(haveDeliveryCharge()) {
            return deliveryInfo.getCharge();
        }
        return 0;
    }
    public int getTotalPaymentPrice() {
        return getTotalOrderPrice() + getDeliveryCharge();
    }

}
