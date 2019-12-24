package main.Lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
    java.util.function 패키지 사용해보기
 */
public class LambdaEx5 {
    public static void main(String[] args) {

        // 매개변수 X 값반환 O
        Supplier<Integer> s = () -> (int) (Math.random() * 100) + 1;

        // 매개변수 O 값반환 X
        Consumer<Integer> c = i -> System.out.print(i + ", ");

        // 매개변수 O 값반환 O(boolean만 가능)
        Predicate<Integer> p = i -> i % 2 == 0;

        // 매개변수 O 값반환 O  오른쪽이 반환값
        Function<Integer, Integer> f = i -> i / 10 * 10; // i의 일의자리를 없앤다.

        List<Integer> list = new ArrayList<>();
        makeRandomList(s, list);

        System.out.println(list);

        printEvenNum(p, c, list);

        List<Integer> newList = doSomething(f, list);

        System.out.println(newList);
    }

    static <T> List<T> doSomething(Function<T, T> f, List<T> list) {
        List<T> newList = new ArrayList<>(list.size());

        for (T i : list) {
            newList.add(f.apply(i));
        }

        return newList;
    }

    static <T> void printEvenNum(Predicate<T> p, Consumer<T> c, List<T> list) {
        System.out.print("[");
        for (T i : list) {
            if (p.test(i)) {
                c.accept(i);
            }
        }
        System.out.println("]");
    }

    static <T> void makeRandomList(Supplier<T> s, List<T> list) {
        for (int i = 0; i < 10; i++) {
            list.add(s.get());
        }
    }
}
