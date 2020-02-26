package me.sun.springbootex1.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("================ CommandLineRunner ================");
        Arrays.stream(args).forEach(System.out::println);
        System.out.println("===================================================");
    }
}
