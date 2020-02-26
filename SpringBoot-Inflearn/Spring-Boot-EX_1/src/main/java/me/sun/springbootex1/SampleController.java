package me.sun.springbootex1;

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
}

@Data
class User {
    private String username;
    private String password;
}
