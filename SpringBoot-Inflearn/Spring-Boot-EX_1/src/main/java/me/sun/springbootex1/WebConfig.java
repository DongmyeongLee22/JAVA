package me.sun.springbootex1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 정적 리소스 핸들링
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/lee/**")
                .addResourceLocations("classpath:/lee/")
                .setCachePeriod(30);
    }
}
