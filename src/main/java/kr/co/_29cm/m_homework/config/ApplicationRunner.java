package kr.co._29cm.m_homework.config;

import kr.co._29cm.m_homework.repository.DataRepository;
import kr.co._29cm.m_homework.view.UserInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void run(Class<?> appClass) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(appClass);
        applicationContext.refresh();

        DataRepository dataRepository = applicationContext.getBean(DataRepository.class);
        dataRepository.initData();

        UserInterface userInterface = applicationContext.getBean(UserInterface.class);
        userInterface.render();
    }
}
