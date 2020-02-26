package me.sun.springbootex1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 기본이 MOCK, 그러므로 서블릿을 실제로 띄우기는게 아닌 Mocking을해서 띄우는 것
 * 그러므로 MockMvc를 통해 클라이언트 요청을 할 수 있다.
 * 만약 RANDOM_PORT로 설정하면 실제 포트가 뜨므로 TestRestTemplate, WebClient로 테스트를 하여야 한다.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringBootEx1ApplicationTests {

    @Autowired
    Environment environment;

    // MCOK이 아닐때는 TestRestTemplate를 사용
//    @Autowired
//    TestRestTemplate testRestTemplate;

//    void hello() throws Exception{
//        final String result = testRestTemplate.getForObject("/hello", String.class);
//        assertThat(result).isEqualTo("Hello");
//    }

    /**
     * MockBean
     * - Mockbean을 사용하면 application context에 들어있는 빈을 mock 객체로 만들어준다.
     * - 모든 테스트마다 자동 리셋해준다.
     * - @WebMvcTest할 때 sampleService를 stubbing해서 단위 테스트할 수 있다.
     * - 단위테스트는 @JsonTest, @WebMvcTest, @DataJpaTest 등이 있다.
     */
    @MockBean
    SampleService sampleService;

    /**
     * 웹플럭스 의존성을 추가하면 WebTestClient를 사용할 수있다.
     * - TestRestTemplate, MockMvc보다 명확하게 테스트할 수 있다.
     */
//    @Autowired
//    WebTestClient webTestClient;
//
//    @Test
//    void webTestClient() throws Exception{
//        webTestClient
//                .get()
//                .uri("/hello")
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(String.class)
//                .isEqualTo("Hello");
//    }
    @Test
    void contextLoads() {
    }

    /**
     * test.resources에 properties만들면 그 properties가 적용됨.
     * - main 컴파일 후 test 컴파일할 때 그 properties가 덮어써진다.
     * - main properties에 있는데 값이 test properties가 없으면 테스트 시 에러 발생(만약 메인에서 해당 값을 쓰면 부트 돌리면서 에러)
     * - 우선순위는 따로 자료를 참고하자
     * - 최상위에 config 폴더를 만들어서 거기에 설정하면 가장 우선순위가 높다.
     */
    @Test
    void propertyTests() throws Exception {
        assertThat(environment.getProperty("dexter.name"))
                .isEqualTo("dexter");
    }

}
