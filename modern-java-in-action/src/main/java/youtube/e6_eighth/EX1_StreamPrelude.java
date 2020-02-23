package youtube.e6_eighth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Stranger on 2020/02/22
 */
public class EX1_StreamPrelude {
    public static void main(String[] args) {
        final int abs1 = Math.abs(-1222);
        final int abs2 = Math.abs(1222);
        System.out.println("abs1 == abs2 " + (abs1 == abs2));
        // true이지만 함수형 프로그래밍에서의 함수의 조건을 만족시키지 못한다.

        // 왜냐하면 2의보수로 인해 MIN_VALUE을 abs해도 MAX_VALUE가 될 수 없다.
        final int minInt = Integer.MIN_VALUE;
        System.out.println("minInt = " + minInt);

        final int maxInt = Math.abs(Integer.MAX_VALUE);

        int absMinInt = Math.abs(minInt);
        System.out.println("absMinInt = " + absMinInt);
        System.out.println("abs(minInt) == maxInt " + (absMinInt == maxInt));

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(map(numbers, i -> i * 2));
        // 만약 mapper가 null이라면?

        System.out.println(mapNull(numbers, null));
        System.out.println(mapNull2(numbers, null));

        System.out.println("============ Better ==============");

        System.out.println(mapBetterVersion(numbers, i -> i * 2));
        System.out.println(mapBetterVersion(numbers, null));

        // null을 넘기지말고 identity를 사용하자
       System.out.println(mapBetterVersion2(numbers, Function.identity()));
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }

    private static <T, R> List<R> mapNull(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (T t : list) {
            if (mapper != null) { // 매번 mapper를 확인하므로 비효율 적이다.
                result.add(mapper.apply(t));
            } else {
                // 잘못된 스멜이 난다.
                result.add((R) t);
            }
        }
        return result;
    }

    private static <T, R> List<R> mapNull2(List<T> list, Function<T, R> mapper) {
        // 이 또한 잘못한 스멜이난다.
        if (mapper == null) return new ArrayList<>((List<R>) list);

        final List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }

    private static <T, R> List<R> mapBetterVersion(final List<T> list, final Function<T, R> mapper) {
        final Function<T, R> function;
        // function = mapper != null ? mapper : t -> (R) t; 타입 캐스팅 해줘야함
        function = mapper != null ? mapper : t -> (R)t;

        final List<R> result = new ArrayList<>();

        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }

    private static <T, R> List<R> mapBetterVersion2(final List<T> list, final Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();

        for (T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}


