package me.sun.springbootex1.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@WebMvcTest(ThymeLeafConfoller.class)
class ThymeLeafConfollerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * View도 테스트 가능하다.
     * - JSP는 서블릿 엔진이 응답으로 내보낼 최종적인 뷰를 만들어주기 때문에 테스트로 랜더링 결과를 확인하기 힘들다.
     * - 하지만 타임리프는 독자적으로 최종적인 뷰를 만들기 때문에 서블릿 컨테이너의 개입없이 MockMvc 테스트로도 확인이 가능하다.
     */
    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/thyme"))
                .andExpect(status().isOk())
                .andExpect(view().name("hello"))
                .andExpect(model().attribute("name", Matchers.is("dexter")));


    }
}