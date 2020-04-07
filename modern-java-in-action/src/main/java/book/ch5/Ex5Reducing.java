package book.ch5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Ex5Reducing {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);


        // ======= 일반 for문 =======
        int sum = 0; // 초기 값
        for (Integer number : numbers) {
            sum += number; // 연산
        }
        System.out.println("sum = " + sum);
        // ======= 리듀싱 연산 ======= : 동일하게 초기값과 연산 과정을 가지고 있다.
        Integer sum2 = numbers.stream().reduce(0, (pre, cur) -> pre + cur);
        // pre + cur의 연산 결과가 다시 pre가 되는 식으로 계속해서 연산이 동작된다.
        System.out.println("sum2 = " + sum2);

        Optional<Integer> sum3Op = numbers.stream().reduce((pre, cur) -> pre + cur);
        System.out.println("sum3Op.get() = " + sum3Op.get());

        Integer min = numbers.stream().reduce(Math::min).get();
        Integer max = numbers.stream().reduce(Math::max).get();

        System.out.println("min = " + min);
        System.out.println("max = " + max);
    }
}
