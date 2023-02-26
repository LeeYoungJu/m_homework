package kr.co._29cm.m_homework.view;

import kr.co._29cm.m_homework.controller.ProductController;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.OrderProduct;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.view.question.OrderOrQuitQuestion;
import kr.co._29cm.m_homework.view.question.ProductOrderQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserInterface {
    private final ProductController productController;
    private final OrderOrQuitQuestion orderOrQuitQuestion;
    private final ProductOrderQuestion productOrderQuestion;

    private boolean quitFlag = false;
    private Scanner scanner = new Scanner(System.in);

    public void render() {
        while(true) {
            orderOrQuitQuestion.ask(scanner
                    , (() -> chooseOrder())
                    , (() -> chooseQuit())
            );

            if(doesUserWantToQuit()) {
                break;
            }
        }
    }
    public void chooseOrder() {
        List<String> colNames = productController.getColNames();
        List<Product> products = productController.getAllProducts();

        PrintUtils.PrintColNames(colNames);
        for(Product product : products) {
            PrintUtils.printProduct(product);
        }

        Order order = Order.builder().id(UUID.randomUUID().toString()).build();

        productOrderQuestion.ask(scanner
                , productId -> productController.isProductIdValidate(productId)
                , () -> {
                    if(order.isProductEmpty()) {
                        return false;
                    }
                    return productController.isStockAmtOk(order);
                }
                , () -> {}
                , (productId, orderAmt) -> {
                    OrderProduct orderProduct = OrderProduct.builder()
                            .ProductId(productId)
                            .orderAmt(orderAmt)
                            .build();

                    order.addProduct(orderProduct);
                });
    }

    private void chooseQuit() {
        quitFlag = true;
    }

    private boolean doesUserWantToQuit() {
        return quitFlag;
    }
}
