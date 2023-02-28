package kr.co._29cm.m_homework.view;

import kr.co._29cm.m_homework.controller.OrderController;
import kr.co._29cm.m_homework.controller.ProductController;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.OrderProduct;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.SoldOutException;
import kr.co._29cm.m_homework.view.question.OrderOrQuitQuestion;
import kr.co._29cm.m_homework.view.question.ProductOrderQuestion;
import kr.co._29cm.m_homework.view.util.PrintUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UserInterface {
    private final ProductController productController;
    private final OrderController orderController;
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

            if(isQuit()) {
                break;
            }
        }
    }
    public void chooseOrder() {
        List<String> colNames = productController.getColNames();
        List<Product> products = productController.getAllProducts();

        PrintUtils.PrintColNames(colNames);
        PrintUtils.printProducts(products);

        /*
         * Order 인스턴스의 생명주기는 사용자가 주문 프로세스를 새로 시작할 때 생성되고
         * 주문 프로세스가 종료될 때 사라져야 하므로 이 위치가 적당하다.
         */
        Order order = orderController.createOrder();

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
                , () -> payFor(order)

                /*
                 * 상품번호, 수량에 제대로 된 값을 모두 입력 후 ENTER 이벤트 발생 시 선택한 상품을 주문에 추가
                 */
                , (productId, orderAmt) -> addProductToOrder(order, productId, orderAmt)
        );
    }

    /*
     * 주문에 추가한 상품들의 재고 확인
     */
    private boolean checkOrderProductStockAmt(Order order) {
        if(order.isProductEmpty()) {
            // 아무 상품도 추가하지 않은 상태일 때
            return false;
        }
        try {
            return productController.isStockAmtOk(order);
        } catch (SoldOutException e) {
            System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            return false;
        }
    }

    private void payFor(Order order) {
        try {
            orderController.payFor(order);
            PrintUtils.printOrderResult(order);
        } catch (SoldOutException e) {
            System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
    }

    private void addProductToOrder(Order order, String productId, int orderAmt) {
        Product product = productController.getProductById(productId);
        OrderProduct orderProduct = OrderProduct.builder()
                .productId(productId)
                .orderAmt(orderAmt)
                .product(product)
                .build();

        order.addProduct(orderProduct);
    }

    private void chooseQuit() {
        quitFlag = true;
    }

    private boolean isQuit() {
        return quitFlag;
    }
}
