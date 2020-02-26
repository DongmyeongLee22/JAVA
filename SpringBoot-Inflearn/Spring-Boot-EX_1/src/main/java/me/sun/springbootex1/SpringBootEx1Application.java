package me.sun.springbootex1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringBootEx1Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(SpringBootEx1Application.class)
                .banner((environment, sourceClass, out) -> {
                    out.println("==============================");
                    out.println("=======Coding Banner==========");
                    out.println("==============================");
                })
                .run(args);
    }
//    public static void main(String[] args) {
//        // SpringApplication.run(SpringBootEx1Application.class, args);
//
//        // 이렇게도 실행 가능
//        final SpringApplication app = new SpringApplication(SpringBootEx1Application.class);
//        // app.setBannerMode(Banner.Mode.OFF); Banner 끄기
//
//        // 코딩으로 배너 설정하기
//        app.setBanner(((environment, sourceClass, out) -> {
//            out.println("==============================");
//            out.println("=======Coding Banner==========");
//            out.println("==============================");
//        }));
//        app.run(args);
//
//        /** 디버그 모드로 동작시키기
//         * 디버그 모드는 어떤한 자동설정이 되었는지 알려준다. 그러므로 필요할 떄 사용하자
//         * VM options -Ddebug
//         */
//
//        /** 배너
//         *  - resources에 banner.txt로 가능
//         *  - 이미지도 사용할 수 있다.
//         *  - application.properties에서도 설정할 수 있다.
//         */
//    }


}
