package kr.co._29cm.m_homework.view.question;

import kr.co._29cm.m_homework.view.UserInterface;
import kr.co._29cm.m_homework.view.consts.Question;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 사용자에게 상품번호, 주문수량을 물어보고 그에 따라 진행되는 흐름을 구현한 클래스
 */
@Component
public class ProductOrderQuestion {
    private final int SPACE_CODE = 32;
    private boolean quitFlag = false;

    /**
     * 사용자에게 상품번호와 주문수량을 순서대로 물어보고 선택에 따라 각종 콜백 함수를 실행한다.
     * @param productIdExistChecker 사용자가 입력한 상품번호에 대한 유효성 체크 함수
     * @param addOrderProductCallback 사용자가 상품번호, 주문수량을 입력하면 상품을 주문에 추가하는 함수
     * @param orderPayRunner 사용자가 주문할 상품을 모두 추가 후 실행되는 주문 체결 프로세스
     */
    public void ask(Function<String, Boolean> productIdExistChecker
            , BiConsumer<String, Integer> addOrderProductCallback
            , Runnable orderPayRunner) {

        while(true) {
            String productId = askProductId(orderPayRunner, productIdExistChecker);

            if(doesUserWantToQuit()) {
                whenQuit();
                break;
            }

            int orderAmt = askOrderAmt();

            addOrderProductCallback.accept(productId, orderAmt);
        }
    }

    /**
     * 사용자에게 상품번호와 주문수량을 물어보고 상황에 따라 콜백함수 실행하고 입력된 상품번호를 반환한다.
     * @param orderPayRunner SPACE + ENTER 시 주문 체결 프로세스
     * @param productIdExistChecker 상품번호 유효성 체크
     * @return 사용자가 입력한 상품번호
     */
    private String askProductId(Runnable orderPayRunner
            , Function<String, Boolean> productIdExistChecker) {
        String productId = "";
        while(true) {
            System.out.print(Question.PRODUCT_ID);
            productId = UserInterface.scanner.nextLine();

            if(isSpaceBar(productId)) {
                orderPayRunner.run();
                chooseQuit();
                break;
            } else {
                if(productIdExistChecker.apply(productId)) {
                    break;
                } else {
                    System.out.println("해당 상품번호는 존재하지 않습니다.");
                }
            }
        }
        return productId;
    }

    /**
     * 사용자에게 주문수량을 물어보고 그 대답을 반환한다.
     * @return 사용자가 입력한 주문수량
     */
    private int askOrderAmt() {
        int orderAmt;
        while(true) {
            System.out.print(Question.STOCK_AMT);
            String stockAmtStr;

            stockAmtStr = UserInterface.scanner.nextLine();

            try {
                orderAmt = Integer.parseInt(stockAmtStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }

        }
        return orderAmt;
    }

    private boolean isSpaceBar(String keyword) {
        return keyword.charAt(0) == SPACE_CODE;
    }

    private void chooseQuit() {
        quitFlag = true;
    }

    private boolean doesUserWantToQuit() {
        return quitFlag;
    }

    private void whenQuit() {
        quitFlag = false;
    }
}
