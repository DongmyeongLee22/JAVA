package youtube.e5_function_utility;

import java.math.BigDecimal;

/**
 * Created by Stranger on 2020/02/22
 */
public class FunctionalInterfaceLimit {
    public static void main(String[] args) {
        BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();
        String result = bigDecimalToCurrency.toCurrency(new BigDecimal("120.00"));
        System.out.println(result);

        InvalidFunctionalInterface invalidFunctionalInterface1 = new InvalidFunctionalInterface() {
            @Override
            public <T> String toString(T value) {
                return value.toString();
            }
        };

        // 제네릭 메서드는 람다 표현식이 사용 불가능하다.
        // 왜냐하면 해당 값을 추론할 수 없기 때문이다.
        // 해당 메서드를 호출 할 때 해당 타입을 알 수 있게 된다.
        // InvalidFunctionalInterface invalidFunctionalInterface = value -> value.toString();

        System.out.println(invalidFunctionalInterface1.toString(123));
    }
}

@FunctionalInterface
interface BigDecimalToCurrency {
    String toCurrency(BigDecimal bd);
}

@FunctionalInterface
interface InvalidFunctionalInterface {
    <T> String toString(T value);
}
