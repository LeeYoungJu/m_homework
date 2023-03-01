package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.entity.*;
import kr.co._29cm.m_homework.exception.SoldOutException;
import kr.co._29cm.m_homework.repository.DataRepository;
import kr.co._29cm.m_homework.service.consts.DeliveryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 주문과 관련된 비즈니스 로직이 구현되는 클래스
 */
@Component
@RequiredArgsConstructor
public class OrderService {
    private final DataRepository dataRepository;
    private final DeliveryInfoService deliveryInfoService;


    /**
     * <pre>
     * 책임 : 주문 객체 생성
     * 설명 : 사용자가 주문을 선택하면 주문 객체가 생성되는데 이 함수를 통해 생성된다.
     * </pre>
     * @Param
     * @Return 생성된 Order 객체
     */
    public Order createOrder() {
        DeliveryInfo deliveryInfo = deliveryInfoService.getDeliveryCharge(DeliveryType.GENERAL);
        return Order.builder()
                .id(UUID.randomUUID().toString())
                .orderProducts(new ArrayList<>())
                .deliveryInfo(deliveryInfo)
                .build();
    }

    /**
     * <pre>
     * 책임 : 주문 결제
     * 설명 : 사용자가 주문할 상품들을 선택하고 최종적으로 주문 결제를 하게 되면 이 함수가 실행된다.
     * 특이사항 : 해당 함수는 상품 수량을 감소시키기 때문에 유저가 몰리면 동시성 이슈가 발생한다.
     *          이를 해결하기 위해 synchronized를 사용해 lock을 걸어서 여러 요청이 와도 순서대로 실행되도록 하였다.
     * </pre>
     * @Param order : 사용자 주문 객체
     * @Return void
     */
    public synchronized void payFor(Order order) throws SoldOutException {
        /*
         * 상품 복사본을 이용해 상품 수량을 감소시키고 감소된 상품 리스트를 가져온다.
         * 이 때 수량이 부족하면 SoldOutException 예외가 발생한다.
         */
        List<BaseEntity> updatedProducts = makeUpdateProducts(order);

        /*
         * 앞선 단계에서 상품 복사본의 수량을 감소시켰다.
         * 이 단계에서는 수량이 감소된 상품을 실제 Data Storage에 업데이트 한다.
         */
        dataRepository.updateCommit(Product.class, updatedProducts);
    }

    /**
     * <pre>
     * 책임 : 수량이 감소된 상품 리스트를 반환
     * 설명 : 주문에 포함된 상품 리스트 복사본을 가져와서 주문수량만큼 재고 수량을 감소시킨다.
     *       이 때 재고 수량이 부족하면 SoldOutException 예외를 발생시킨다.
     * </pre>
     * @Param order : 사용자 주문 객체
     * @Return 수량이 감소된 상품 리스트 (BaseEntity로 반환)
     */
    private List<BaseEntity> makeUpdateProducts(Order order) throws SoldOutException {
        // 주문에 포함된 상품 리스트 복사본을 가져온다.
        List<Product> products = getProductsFromOrder(order);

        List<BaseEntity> newProducts = new ArrayList<>();
        for(int i = 0; i < products.size(); i++) {
            newProducts.add(
                    products.get(i).decreaseStockAmt(order.getOrderProducts().get(i).getOrderAmt())
            );
        }

        return newProducts;
    }

    /**
     *<pre>
     * 책임 : 주문 객체의 상품 리스트 복사본 반환
     *</pre>
     * @Param order : 사용자 주문 객체
     * @Return 상품 리스트(복사본)
     */
    private List<Product> getProductsFromOrder(Order order) {
        List<OrderProduct> orderProducts = order.getOrderProducts();
        List<String> productIds =  orderProducts
                .stream()
                .map(orderProduct -> orderProduct.getProduct().getId())
                .collect(Collectors.toList());

        return dataRepository.selectByIds(Product.class, productIds);
    }
}
