package me.sun.springbootex1;

import me.sun.springbootex1.listener.SampleListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEx1Application {


    // Builder로도 App 실행 가능
//    public static void main(String[] args) {
//        new SpringApplicationBuilder()
//                .sources(SpringBootEx1Application.class)
//                .banner((environment, sourceClass, out) -> {
//                    out.println("==============================");
//                    out.println("=======Coding Banner==========");
//                    out.println("==============================");
//                })
//                .run(args);
//    }
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(SpringBootEx1Application.class);
        app.addListeners(new SampleListener());
        // 빈 생성 이전의 이벤트는 따로 등록해주면 동작한다.

        app.setWebApplicationType(WebApplicationType.NONE);
        // 설정에 SERVLET이 존재하면 기본이 SERVLET
        // 서블릿이 1순위 이므로 웹플러긋, 서블릿이 둘 다 있으면 서블릿이 먼저 등록된다.
        // 그러므로 REACTIVE로 변경하면된다.

        app.run(args);
    }
}
