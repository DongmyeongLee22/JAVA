package youtube.e6_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Stranger on 2020/02/22
 */
public class EX3_StreamExamples2 {
    public static void main(String[] args) {
        // Stream은 일종의 Collection Builder라고 할 수 있다.

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = null;
        // 얘는 따져보면 약 15번 연산을 수행한다.
        AtomicInteger operCount = new AtomicInteger();
        for (Integer number : numbers) {
            operCount.getAndIncrement();
            if (number > 3) {
                operCount.getAndIncrement();
                if (number < 9){
                    operCount.getAndIncrement();
                    Integer newNumber = number * 2;
                    operCount.getAndIncrement();
                    if (newNumber > 10) {
                        result = newNumber;
                        break;
                    }
                }
            }
        }
        System.out.println("result = " + result);
        System.out.println("operCount = " + operCount);

        // 3 ~ 9의 값중 두배해서 10 보다큰 가장 첫번째 숫자
        // 얘는 따져보면 약 27번 연산을 수행할거 같다.
        operCount.set(0);
        System.out.println("operCount = " + operCount);
        Integer integer = numbers.stream()
                .filter(num -> {
                    operCount.getAndIncrement();
                    return num > 3;
                })
                .filter(num -> {
                        operCount.getAndIncrement();
                        return num < 9;
                })
                .map(num -> {
                    operCount.getAndIncrement();
                    return num * 2;
                })
                .filter(num -> {
                    operCount.getAndIncrement();
                    return num > 10;
                })
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        System.out.println("integer = " + integer);

        // 하지만 결과는 다르다.
        // Stream은 findFirst()를 보고 그 후에 아 값을 찾아야겠다고 생각하고 이전의 filter, map을 찾게된다.
        System.out.println("operCount = " + operCount);

        // 직접 만들어서 사용하면 하나씩 다 연산을 하므로 27번의 연산을 수행한다.
        List<Integer> greaterThan3 = filter(numbers, i -> i > 3);
        List<Integer> lessThan9 = filter(greaterThan3, i -> i < 9);
        List<Integer> doubled = map(lessThan9, i -> i * 2);
        List<Integer> greaterThan10 = filter(doubled, i -> i > 10);
        System.out.println(greaterThan10.get(0));
    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> p){
        final List<T> ret = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)){
                ret.add(t);
            }
        }
        return ret;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper){
        final List<R> ret = new ArrayList<>();
        for (T t : list) {
            ret.add(mapper.apply(t));
        }
        return ret;
    }
}
