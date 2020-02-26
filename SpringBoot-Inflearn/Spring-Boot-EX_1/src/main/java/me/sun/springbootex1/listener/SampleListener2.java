package me.sun.springbootex1.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Component
public class SampleListener2 implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("===========================");
        System.out.println("Application Started Event");
        System.out.println("===========================");
    }
}
