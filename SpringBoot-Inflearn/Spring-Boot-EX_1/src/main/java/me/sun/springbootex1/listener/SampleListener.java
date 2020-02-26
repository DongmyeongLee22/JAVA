package me.sun.springbootex1.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {
    /**
     * Application Context 발생 후 의 이벤트일 경우 빈으로 등록하면 이벤트가 동작하지만
     * Application Context 발생 이전의 이벤트는 빈을 등록하기 전이므로 빈을 등록하더라고 동작되지 않는다.
     * 이런 경우 직접 등록해주면 제대로 동작한다.(Main 참조)
     */
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("===========================");
        System.out.println("Application is starting");
        System.out.println("===========================");
    }
}
