package me.sun.springbootex1.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@RestController
public class SampleController {
    /** HttpMessageConverter
     *  - HTTP 요청 본문을 객체로, 객체를 HTTP 응답 본문으로 변경할 때 사용
     *  - 요청받은 데이터가 JSON이라면 JSONMessageConverter로 인해 객체로 컨버팅 해준다.
     *  - 응답 메시지 변환은 컴포지션 타입일 경우는 기본적으로 JSON으로 변환해주고 String같은 기본타입일 경우 스트링메시지컨버터로 스트링으로변환된다.
     */

    /**
     * ViewResolver
     * ContentNegotiatingViewResolver
     * - 들어오는 요청의 acceptheader에 따라 서로다른 응답을 해주는 ViewResolver
     * - acceptheader가 없는 요청을 대비하기 위해 /path?format=xml 이런식으로 알려줄 수 있다.
     * - 예전에 지원해주던 /path.json (.타입)은 이제 사용할 수 없다.
     */
    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        return user;
    }

    /** 스프링부트가 JSP를 권장하지 않음
     *  - 스프링부트는 독립적으로 실행가능한 JAR로 패키징하고 임베디드 톰캣으로 애플리케이션을 빠르고 쉽게 배포하길 바란다.
     *  - 하지만 JSP를 사용하면 JAR로 패키징할 수 없고 WAR로 패키징해야한다.
     *  - 가장 최근에 만들어진 서블릿 엔진인 Undertow는 JSP자체를 지원하지도 않는다.
     */
}

@Data
class User {
    private String username;
    private String password;
}
