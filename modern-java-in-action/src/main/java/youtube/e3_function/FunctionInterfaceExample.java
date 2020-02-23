package youtube.e3_function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Stranger on 2020/02/22
 */
public class FunctionInterfaceExample {
    // Function은 T -> R로 변환해주는 함수이다.
    public static void main(String[] args) {
        temp(a -> a.toUpperCase(), "Hello");

        Function<String, Integer> toInt = s -> Integer.valueOf(s);

        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> filter = filter(integerList, i -> i < 5);
        System.out.println(filter);
    }

    static <T, R> void temp(Function<T, R> fn, T data) {
        R apply = fn.apply(data);
        System.out.println(apply);
    }

    static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> ret = new ArrayList<>();
        list.forEach(l -> {
            if(p.test(l)) ret.add(l);
        });
        return ret;
    }
}
