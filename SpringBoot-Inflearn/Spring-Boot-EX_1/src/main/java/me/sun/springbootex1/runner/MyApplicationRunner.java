package me.sun.springbootex1.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */

/**
 * CommandLineRunner보다 파라미터가 좋기때문에 Runner 사용을 권장
 */
@Component
@Order(0)
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("================== ApplicationRunner(Order 0) ==================");
        System.out.println("foo: " + args.containsOption("foo"));
        System.out.println("bar: " + args.containsOption("bar"));
        System.out.println("========================================================");
    }
}
