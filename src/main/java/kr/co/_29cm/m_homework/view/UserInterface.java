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
    public static Scanner scanner = new Scanner(System.in);

    /**
     * 앱이 시작되면 render 함수가 실행된다.
     * 화면 흐름을 컨트롤한다.
     */
    public void render() {
        while(true) {
            /*
             * 사용자에게 주문 프로세스를 진행할지 종료할지 물어본다.
             */
            orderOrQuitQuestion.ask(
                    /*
                     * 사용자가 주문 프로세스를 선택하면 아래 함수를 실행한다.
                     */
                    (() -> chooseOrder())
                    /*
                     * 사용자가 종료를 선택하면 아래 함수를 실행한다.
                     */
                    , (() -> chooseQuit())
            );

            /*
             * 사용자가 종료를 선택하면 아래와 같이 break가 실행되면서 render 함수가 종료된다.
             * render 함수의 종료는 곧 앱의 종료이다.
             */
            if(isQuit()) {
                break;
            }
        }
    }

    /**
     * <pre>
     * 사용자가 주문 프로세스를 선택했을 때 실행된다.
     * 이 함수 스코프에서 주문이 새로 생성되고 소멸된다.
     * 사용자는 반복적으로 상품을 선택/추가할 수 있다.
     * </pre>
     */
    public void chooseOrder() {
        /*
         * 사용자가 주문 프로세스를 선택하면 상품 리스트를 화면에 보여준다.
         */
        List<String> colNames = productController.getProductColNames();
        List<Product> products = productController.getAllProducts();
        PrintUtils.PrintColNames(colNames);
        PrintUtils.printProducts(products);

        /*
         * Order 인스턴스의 생명주기는 사용자가 주문 프로세스를 새로 시작할 때 생성되고
         * 주문 프로세스가 종료될 때 사라져야 하므로 이 위치가 적당하다.
         */
        Order order = orderController.createOrder();

        /*
         * 사용자에게 주문할 상품번호, 수량을 (반복적으로) 물어본다.
         */
        productOrderQuestion.ask(
                /*
                 * 상품번호 입력 후 ENTER 이벤트 발생 시 해당 상품번호 유효성 체크
                 */
                productId -> productController.isProductIdValidate(productId)
                /*
                 * 상품번호, 수량에 제대로 된 값을 모두 입력 후 ENTER 이벤트 발생 시 선택한 상품을 주문에 추가
                 */
                , (productId, orderAmt) -> addProductToOrder(order, productId, orderAmt)
                /*
                 * 상품번호 입력 단계에서 SPACE+ENTER 이벤트 발생 시 주문 체결 프로세스
                 */
                , () -> payFor(order)
        );
    }

    /**
     * 주문 체결 프로세스
     * @param order 주문 객체
     */
    private void payFor(Order order) {
        // 주문에 추가된 상품이 없으면 프로세스를 진행하지 않는다.
        if(order.isProductEmpty()) {
            return;
        }

        try {
            orderController.payFor(order);
            PrintUtils.printOrderResult(order);
        } catch (SoldOutException e) {
            System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
    }

    /**
     * 사용자가 상품번호, 주문수량을 입력하면 주문 객체에 주문상품 객체 형태로 추가한다.
     * @param order 주문 객체
     * @param productId 상품번호
     * @param orderAmt 주문수량
     */
    private void addProductToOrder(Order order, String productId, int orderAmt) {
        Product product = productController.getProductById(productId);
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .orderAmt(orderAmt)
                .build();

        order.addProduct(orderProduct);
    }

    /**
     * 종료 프로세스
     */
    private void chooseQuit() {
        quitFlag = true;
    }

    /**
     * 사용자가 종료를 선택했는지 아닌지 체크
     * @return 종료 여부(true/false)
     */
    private boolean isQuit() {
        return quitFlag;
    }
}
