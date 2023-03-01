package kr.co._29cm.m_homework.config;

import kr.co._29cm.m_homework.database.DataLoaderFactory;
import kr.co._29cm.m_homework.view.UserInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <pre>
 * 어플리케이션이 실행되면 해당 클래스의 run 함수가 실행된다. (시작점)
 * 주요 기능은 스프링 컨테이너에 어노테이션 기반 bean을 올려서 관리하도록 하는 것이다.
 * </pre>
 */
public class ApplicationRunner {

    /**
     * <pre>
     * 1. 스프링 컨테이너에 Bean을 등록하고 의존성 관계를 설정한다.
     * 2. 파일로부터 데이터를 읽어서 메모리 데이터 저장소에 올린다.
     * 3. 사용자에게 주문할 수 있는 화면을 보여준다.
     * </pre>
     * @param appClass
     */
    public static void run(Class<?> appClass) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(appClass);
        applicationContext.refresh();

        System.out.println("loading data...");
        DataLoaderFactory dataLoaderFactory = applicationContext.getBean(DataLoaderFactory.class);
        dataLoaderFactory.getLoader().loadDataOnMemory();

        UserInterface userInterface = applicationContext.getBean(UserInterface.class);
        userInterface.render();
    }
}
