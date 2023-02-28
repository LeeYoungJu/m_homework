package kr.co._29cm.m_homework.config;

import kr.co._29cm.m_homework.database.DataLoaderFactory;
import kr.co._29cm.m_homework.view.UserInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void run(Class<?> appClass) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(appClass);
        applicationContext.refresh();

        /*
         * Step1. Application 을 시작하면 먼저 파일에 있는 데이터를 메모리에 올린다.
         */
        System.out.println("loading data...");
        DataLoaderFactory dataLoaderFactory = applicationContext.getBean(DataLoaderFactory.class);
        dataLoaderFactory.getLoader().loadDataOnMemory();

        /*
         * Step2. 사용자가 마주하는 첫 화면을 그려준다.
         */
        UserInterface userInterface = applicationContext.getBean(UserInterface.class);
        userInterface.render();
    }
}
