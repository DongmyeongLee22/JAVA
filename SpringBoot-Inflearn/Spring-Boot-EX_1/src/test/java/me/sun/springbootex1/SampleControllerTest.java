package me.sun.springbootex1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createUser_JSON() throws Exception {
        String userJson = "{\"username\":\"dexter\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("dexter"))
                .andExpect(jsonPath("password").value("123"));

    }

    /**
     * 요청은 JSON 응답은 XML로해도 XML Message Converter를 추가해주기만 하면 ContentNegotiatingViewResolver
     * 뷰리졸버랑 메시지 컨버터가 연관이 되어있어 알아서 변환해준다.
     */
    @Test
    void createUser_XML() throws Exception {
        String userJson = "{\"username\":\"dexter\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_XML)
                .content(userJson)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username")
                        .string("dexter"))
                .andExpect(xpath("/User/password")
                        .string("123"));

    }

}