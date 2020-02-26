package me.sun.springbootex1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootEx1ApplicationTests {

    @Autowired
    Environment environment;

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
