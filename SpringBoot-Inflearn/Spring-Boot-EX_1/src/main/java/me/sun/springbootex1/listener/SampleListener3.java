package me.sun.springbootex1.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Component
public class SampleListener3 {

    // ApplicationArguments는 VM options의 -D는 VM options으로 치고 argument만 받는다.
    // java -jar build/libs/spring-boot-ex1-0.0.1-SNAPSHOT.jar -Dfoo --bar  터미널로 실행해도 결과는 동일
    public SampleListener3(ApplicationArguments arguments) {
        System.out.println("foo: " + arguments.containsOption("foo"));
        System.out.println("bar: " + arguments.containsOption("bar"));
    }
}
