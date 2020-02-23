package youtube.e8_tenth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by Stranger on 2020/02/23
 */
public class EX1_HigherOrderFunctionExamples {
    public static void main(String[] args) {
        // f: Higher-Order Function
        // > function이 function을 받을 때
        final Function<Function<Integer, String>, String> f = g -> g.apply(10);

        System.out.println(f.apply(i -> "#" + i)); // "#10"

        // > function이 function을 리턴할 때
        final Function<Integer, Function<Integer, Integer>> f2 = i -> i2 -> i + i2;
        System.out.println(
                // f2.apply()는 함수를 리턴하므로 그 함수에 또 apply를 해주면 된다.
                f2.apply(1).apply(25)
        );

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 이 메서드도 Higher Order function이다.

        /*
            first class citizen 기준
            - element가 파라미터로 전달 될 수 있다.
            - element를 리턴할 수 있다.
            - element를 자료구조, 변수에 저장할 수 있다.

            여기서 element가 function인 것을 first class function
            JAVA에서는 function이 method이다.

            - map method를 보면 map method는 first class citizen의 기준에 만족하지 못한다.
            - 하지만 이것도 higer-Order function이라고 한다.
            - method reference를 통해 method 자체도 function으로 쓸 수 있게 하여 first class citizen을 보장 시켜준다.
         */
        System.out.println(map(list, i -> "#" + i));

        System.out.println(
                list.stream()
                    .map(i -> "#" + i) // stream map, filer, etc...도 Higher-Order function
                    .collect(toList())
        );

        Function<Object, Object> identity = Function.identity();// return 값이 function이므로 Higher-Order function이다.

        Function<Integer, Function<Integer, Function<Integer, Integer>>> f3 =
                i1 -> i2 -> i3 -> i1 + i2 + i3;

        // 커링함수가 이렇게 구현됨
        System.out.println(f3.apply(1).apply(2).apply(3));

        Function<Integer, Function<Integer, Integer>> plus10 = f3.apply(10);

    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}
