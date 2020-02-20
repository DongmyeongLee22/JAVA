import org.junit.jupiter.api.*;

class SampleTest implements InterfaceTest {
    @BeforeAll
    void çBeforeAll() {
        System.out.println("------------- SampleTest Before All -------------");
    }

    @BeforeEach
    void sample_BeforeEach() {
        System.out.println("------------- SampleTest Before Each -------------");
    }

    @Test
    void sampleTest(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Sample Test <<<<<<<<<<<<<<<<<<<<<<");
    }

    @AfterEach
    void sample_AfterEach() {
        System.out.println("------------- SampleTest After Each -------------");
    }

    @AfterAll
    void sample_AfterAll() {
        System.out.println("------------- SampleTest After All -------------");
    }
}
