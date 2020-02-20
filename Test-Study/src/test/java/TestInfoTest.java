import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("테스트 인포")
public class TestInfoTest {
    TestInfoTest(TestInfo testInfo){
        assertEquals("테스트 인포", testInfo.getDisplayName());
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo){
        String displayName = testInfo.getDisplayName();
        assertTrue(displayName.equals("First Test") || displayName.equals("secondTest()"));
    }

    @Test
    @DisplayName("First Test")
    @Tag("tag-Info")
    void firstTest(TestInfo testInfo){
        String displayName = testInfo.getDisplayName();
        Set<String> tags = testInfo.getTags();
        assertTrue(displayName.equals("First Test") && tags.contains("tag-Info"));
    }

    @Test
    void secondTest(){
    }

}
