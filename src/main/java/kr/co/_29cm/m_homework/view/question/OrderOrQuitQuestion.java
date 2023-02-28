package kr.co._29cm.m_homework.view.question;

import kr.co._29cm.m_homework.view.consts.Question;
import kr.co._29cm.m_homework.view.consts.QuestionOption;
import kr.co._29cm.m_homework.view.consts.Warning;
import kr.co._29cm.m_homework.view.inferface.QuestionCallback;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class OrderOrQuitQuestion {
    public void ask(Scanner scanner
            , QuestionCallback oCallback
            , QuestionCallback qCallback) {
        System.out.print(Question.ORDER_OR_QUIT);
        String answer = scanner.nextLine();
        if(isAnswerOrder(answer)) {
            oCallback.afterAnswer();
        } else if (isAnswerQuit(answer)) {
            qCallback.afterAnswer();
        } else {
            System.out.println(Warning.WRONG_ANSWER_O_OR_Q);
        }
    }

    private boolean isAnswerOrder(String answer) {
        return QuestionOption.OrderOrQuit.ORDER.contains(answer);
    }

    private boolean isAnswerQuit(String answer) {
        return QuestionOption.OrderOrQuit.QUIT.contains(answer);
    }
}
