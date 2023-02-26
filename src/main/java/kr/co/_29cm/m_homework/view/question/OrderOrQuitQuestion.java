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
        if(answer.equals(QuestionOption.OrderOrQuit.ORDER)) {
            oCallback.afterAnswer();
        } else if (answer.equals(QuestionOption.OrderOrQuit.QUIT)) {
            qCallback.afterAnswer();
        } else {
            System.out.println(Warning.WRONG_ANSWER_O_OR_Q);
        }
    }
}
