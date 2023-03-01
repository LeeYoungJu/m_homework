package kr.co._29cm.m_homework.view.question;

import kr.co._29cm.m_homework.view.UserInterface;
import kr.co._29cm.m_homework.view.consts.Question;
import kr.co._29cm.m_homework.view.consts.QuestionOption;
import kr.co._29cm.m_homework.view.consts.Warning;
import org.springframework.stereotype.Component;

/**
 * 사용자에게 주문 프로세스를 진행할지 종료할지 물어보는 로직을 구현한 클래스
 */
@Component
public class OrderOrQuitQuestion {

    /**
     * <pre>
     * 사용자에게 주문 프로세스를 진행할지 종료할지 물어보고
     * 선택에 따라 정해진 콜백 함수를 실행한다.
     * </pre>
     * @param orderChooseCallback 주문 프로세스 함수
     * @param quitChooseCallback 종료 프로세스 함수
     */
    public void ask(Runnable orderChooseCallback, Runnable quitChooseCallback) {
        System.out.print(Question.ORDER_OR_QUIT);
        String answer = UserInterface.scanner.nextLine();
        if(isAnswerOrder(answer)) {
            orderChooseCallback.run();
        } else if (isAnswerQuit(answer)) {
            quitChooseCallback.run();
        } else {
            System.out.println(Warning.WRONG_ANSWER_O_OR_Q);
        }
    }

    /**
     * 사용자가 입력한 단어가 주문 프로세스인지 체크
     * @param answer 사용자 입력 단어
     * @return
     */
    private boolean isAnswerOrder(String answer) {
        return QuestionOption.OrderOrQuit.ORDER.contains(answer);
    }

    /**
     * 사용자가 입력한 단어가 종료 프로세스인지 체크
     * @param answer 사용자 입력 단어
     * @return
     */
    private boolean isAnswerQuit(String answer) {
        return QuestionOption.OrderOrQuit.QUIT.contains(answer);
    }
}
