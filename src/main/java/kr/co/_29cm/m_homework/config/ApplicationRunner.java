package kr.co._29cm.m_homework.config;

import kr.co._29cm.m_homework.database.DataVirtualStorage;
import kr.co._29cm.m_homework.view.UserInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void run(Class<?> appClass) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(appClass);
        applicationContext.refresh();

        DataVirtualStorage dataVirtualStorage = applicationContext.getBean(DataVirtualStorage.class);
        dataVirtualStorage.initData();

        UserInterface userInterface = applicationContext.getBean(UserInterface.class);
        userInterface.render();
    }
}
