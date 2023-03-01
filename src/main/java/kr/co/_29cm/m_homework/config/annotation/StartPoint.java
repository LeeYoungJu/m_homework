package kr.co._29cm.m_homework.config.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 어플리케이션의 시작점을 나타내는 어노테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan
public @interface StartPoint {
}
