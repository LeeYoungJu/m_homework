package kr.co._29cm.m_homework.view.question;

import kr.co._29cm.m_homework.view.consts.Question;
import kr.co._29cm.m_homework.view.inferface.ProductIdExistChecker;
import kr.co._29cm.m_homework.view.inferface.WhenSpaceBarToProductId;
import kr.co._29cm.m_homework.view.inferface.QuestionCallback;
import kr.co._29cm.m_homework.view.inferface.SelectProductCallback;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ProductOrderQuestion {
    private final int SPACE_CODE = 32;
    private boolean quitFlag = false;

    public void ask(Scanner scanner
            , ProductIdExistChecker productIdExistChecker
            , WhenSpaceBarToProductId whenSpaceBarToProductId
            , QuestionCallback whenFinishSelectProduct
            , SelectProductCallback whenSelectProduct) {

        while(true) {
            String productId = askProductId(scanner, whenSpaceBarToProductId, productIdExistChecker);

            if(doesUserWantToQuit()) {
                break;
            }

            int orderAmt = askOrderAmt(scanner, productId, whenFinishSelectProduct);

            if(doesUserWantToQuit()) {
                break;
            }

            whenSelectProduct.afterSelect(productId, orderAmt);
        }
    }

    private String askProductId(Scanner scanner
            , WhenSpaceBarToProductId whenSpaceBarToProductId
            , ProductIdExistChecker productIdExistChecker) {
        String productId = "";
        while(true) {
            System.out.print(Question.PRODUCT_ID);
            productId = scanner.nextLine();

            if(isSpaceBar(productId)) {
                if(!whenSpaceBarToProductId.checkStockAmt()) {
                    chooseQuit();
                }
                break;
            } else {
                if(productIdExistChecker.isExist(productId)) {
                    break;
                } else {
                    System.out.println("해당 상품번호는 존재하지 않습니다.");
                }
            }
        }
        return productId;
    }

    private int askOrderAmt(Scanner scanner, String productId
            , QuestionCallback whenFinishSelectProduct) {
        int orderAmt = 0;
        while(true) {
            System.out.print(Question.STOCK_AMT);
            String stockAmtStr = "";

            stockAmtStr = scanner.nextLine();

            if (isSpaceBar(productId) && isSpaceBar(stockAmtStr)) {
                whenFinishSelectProduct.afterAnswer();
                chooseQuit();
                break;
            } else {
                try {
                    orderAmt = Integer.parseInt(stockAmtStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력하세요.");
                }
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
}
