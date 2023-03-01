package kr.co._29cm.m_homework.view.util;

import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.OrderProduct;
import kr.co._29cm.m_homework.entity.Product;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

/**
 * 텍스트를 화면에 표시할 때 사용되는 유틸 클래스
 */
public class PrintUtils {
    private static String TAB = "\t\t";

    /**
     * 엔티티 컬럼명 리스트 출력
     * @param colNames
     */
    public static void PrintColNames(List<String> colNames) {
        for(String name : colNames) {
            System.out.print(name + TAB);
        }
        System.out.println("");
    }

    /**
     * 상품 데이터 리스트 출력
     * @param products 출력할 상품 데이터 리스트
     */
    public static void printProducts(List<Product> products) {
        for(Product product : products) {
            System.out.print(product.getId() + TAB);
            System.out.print(product.getName() + TAB);
            System.out.print(product.getPrice() + TAB);
            System.out.print(product.getStockAmt() + TAB);
            System.out.println("");
        }
    }

    /**
     * 주문 체결 프로세스가 완료된 후 주문결과 출력
     * @param order 주문 객체
     */
    public static void printOrderResult(Order order) {

        System.out.println("주문 내역 : ");
        System.out.println("-----------------------------------------");
        order.getOrderProducts().stream()
                .sorted(Comparator.comparing(o -> o.getProduct().getId()))
                .forEach(orderProduct ->System.out.println(formatProductStr(orderProduct)));
        System.out.println("-----------------------------------------");
        System.out.println("주문금액 : " + formatPriceStr(order.getTotalOrderPrice()));
        if(order.haveDeliveryCharge()) {
            System.out.println("배송비 : " + formatPriceStr(order.getDeliveryCharge()));
        }
        System.out.println("-----------------------------------------");
        System.out.println("지불금액 : " + formatPriceStr(order.getTotalPaymentPrice()));
        System.out.println("-----------------------------------------");
        System.out.println("");
        System.out.println("");
    }

    /**
     * 주문 상품의 화면표시 포맷을 만드는 함수
     * @param orderProduct 주문상품 객체
     * @return 주문상품의 화면표시 포맷
     */
    private static String formatProductStr(OrderProduct orderProduct) {
        return orderProduct.getProduct().getName() + " - " + orderProduct.getOrderAmt() + "개";
    }

    /**
     * 가격의 화면표시 포맷을 만드는 함수
     * @param price 가격
     * @return 가격의 화면표시 포맷
     */
    private static String formatPriceStr(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + "원";
    }
}
