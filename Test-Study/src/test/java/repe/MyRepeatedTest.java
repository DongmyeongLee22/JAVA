package repe;

import org.junit.jupiter.api.*;

class MyRepeatedTest {
    @BeforeEach
    void beforeEach(RepetitionInfo repetitionInfo, TestInfo testInfo){
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();

        System.out.printf("%s 테스트 실행 %d 번째 중 %d 번째 \n", methodName, totalRepetitions, currentRepetition);

    }

    @RepeatedTest(3)
    void sampleTest(){
        System.out.println("------- 테스트 수행 중 -------\n");
    }

}
