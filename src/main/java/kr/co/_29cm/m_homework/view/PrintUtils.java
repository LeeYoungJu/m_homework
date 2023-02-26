package kr.co._29cm.m_homework.view;

import kr.co._29cm.m_homework.entity.Product;

import java.util.List;

public class PrintUtils {
    private static String TAB = "\t\t";

    public static void PrintColNames(List<String> colNames) {
        for(String name : colNames) {
            System.out.print(name + TAB);
        }
        System.out.println("");
    }
    public static void printProduct(Product product) {
        System.out.print(product.getId() + TAB);
        System.out.print(product.getName() + TAB);
        System.out.print(product.getPrice() + TAB);
        System.out.print(product.getStockAmt() + TAB);
        System.out.println("");
    }
}
