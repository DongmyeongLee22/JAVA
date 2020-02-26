package me.sun.springbootex1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootEx1Application {

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(SpringBootEx1Application.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }

    /** 로깅 퍼사드(SLF4j) VS 로거(Log4J2, Logback)
     *  - 로깅 퍼사드는 로거들을 추상화한 하나의 기술?이다.
     *  - 로깅 퍼사드를 사용하면 로거들을 바꾸어도 문제가 발생하지 않는다.
     */
}
