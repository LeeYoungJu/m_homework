package kr.co._29cm.m_homework.view;

import kr.co._29cm.m_homework.controller.ProductController;
import kr.co._29cm.m_homework.exception.SoldOutException;
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

        /*
         * 사용자에게 주문할 상품번호, 수량을 물어본다.
         */
        productOrderQuestion.ask(scanner
                /*
                 * 상품번호 입력 후 ENTER 이벤트 발생 시 해당 상품번호 유효성 체크
                 */
                , productId -> productController.isProductIdValidate(productId)

                /*
                 * 상품번호 입력 단계에서 SPACE+ENTER 이벤트 발생 시 재고 체크
                 */
                , () -> checkOrderProductStockAmt(order)

                /*
                 * 상품 추가 완료 후 상품번호, 수량 입력 단계에서 모두 SPACE+ENTER 이벤트 발생 시 주문 결제
                 */
                , () -> {
                    productController.pay();
                }

                /*
                 * 상품번호, 수량에 제대로 된 값을 모두 입력 후 ENTER 이벤트 발생 시 선택한 상품을 주문에 추가
                 */
                , (productId, orderAmt) -> {
                    OrderProduct orderProduct = OrderProduct.builder()
                            .ProductId(productId)
                            .orderAmt(orderAmt)
                            .build();

                    order.addProduct(orderProduct);
                });
    }

    /*
     * 주문에 추가한 상품들의 재고 확인
     */
    private boolean checkOrderProductStockAmt(Order order) {
        if(order.isProductEmpty()) {
            // 아무 상품도 추가하지 않은 상태에서
            return false;
        }
        try {
            return productController.isStockAmtOk(order);
        } catch (SoldOutException e) {
            System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            return false;
        }
    }

    private void chooseQuit() {
        quitFlag = true;
    }

    private boolean doesUserWantToQuit() {
        return quitFlag;
    }
}
