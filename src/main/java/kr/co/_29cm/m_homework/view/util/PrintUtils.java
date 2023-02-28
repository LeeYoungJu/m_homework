package kr.co._29cm.m_homework.view.util;

import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.OrderProduct;
import kr.co._29cm.m_homework.entity.Product;

import java.text.DecimalFormat;
import java.util.List;

public class PrintUtils {
    private static String TAB = "\t\t";

    public static void PrintColNames(List<String> colNames) {
        for(String name : colNames) {
            System.out.print(name + TAB);
        }
        System.out.println("");
    }
    public static void printProducts(List<Product> products) {
        for(Product product : products) {
            System.out.print(product.getId() + TAB);
            System.out.print(product.getName() + TAB);
            System.out.print(product.getPrice() + TAB);
            System.out.print(product.getStockAmt() + TAB);
            System.out.println("");
        }
    }

    public static void printOrderResult(Order order) {
        System.out.println("주문 내역 : ");
        System.out.println("-----------------------------------------");
        order.getOrderProducts().forEach(orderProduct ->
                System.out.println(formatProductStr(orderProduct)));
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

    private static String formatProductStr(OrderProduct orderProduct) {
        return orderProduct.getProduct().getName() + " - " + orderProduct.getOrderAmt() + "개";
    }

    private static String formatPriceStr(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + "원";
    }
}
