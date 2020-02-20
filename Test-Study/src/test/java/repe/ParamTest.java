package repe;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParamTest {

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "C"})
    void stringTest(String str){
        System.out.print(str + " "); // A B C
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void intsTest(int i){
        System.out.print(i + " "); // 1 2 3
    }
}
