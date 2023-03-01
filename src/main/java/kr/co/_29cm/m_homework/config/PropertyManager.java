package kr.co._29cm.m_homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 설정파일의 프로퍼티 값을 읽을 수 있는 클래스
 */
@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class PropertyManager implements EnvironmentAware {

    private Environment environment;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String key) {
        return environment.getProperty(key).trim();
    }

    public String getProperty(String key, String defaultVal) {
        return environment.getProperty(key, defaultVal).trim();
    }
}
