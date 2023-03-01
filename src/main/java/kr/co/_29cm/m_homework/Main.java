package kr.co._29cm.m_homework;

import kr.co._29cm.m_homework.config.ApplicationRunner;
import kr.co._29cm.m_homework.config.annotation.StartPoint;


/**
 * 애플리케이션 시작점
 */
@StartPoint
public class Main {

    /**
     * 시작 main 함수
     * @param args
     */
    public static void main(String[] args) {
        ApplicationRunner.run(Main.class);
    }

}
