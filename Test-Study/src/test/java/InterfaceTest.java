import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
interface InterfaceTest {
    @BeforeAll
    default void beforeAll(){
        System.out.println("============== InterfaceTest Before All =============== ");
    }

    @BeforeEach
    default void beforeEach(){
        System.out.println("============== InterfaceTest Before Each =============== ");
    }

    @AfterEach
    default void afterEach(){
        System.out.println("============== InterfaceTest After Each =============== ");
    }

    @AfterAll
    default void afterAll(){
        System.out.println("============== InterfaceTest After All =============== ");
    }
}
