package me.sun.springbootex1.runner;

import me.sun.springbootex1.SpringBootEx1Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Component
public class LoggerRunner implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(SpringBootEx1Application.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("============ Info Logger ============");
        logger.debug("============ Debug Logger ============");
        logger.trace("============ Trace Logger ============");
    }
}
