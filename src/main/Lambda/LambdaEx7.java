package main.Lambda;

import javax.print.attribute.standard.Finishings;
import java.util.function.Function;
import java.util.function.Predicate;

/*
    하나의 메서드만 호출하는 람다식은 '클래스이름::메서드이름'또는 '참조변수::메서드이름' 이름으로 바꿀 수 있다.

 */
public class LambdaEx7 {
    public static void main(String[] args) {
        Function<String, Integer> f = s -> Integer.parseInt(s, 16);
        Function<Integer, String> g = i -> Integer.toBinaryString(i);

        // f -> g
        Function<String, String> h = f.andThen(g);

        // g - > f
        Function<Integer, Integer> h2 = f.compose(g);

        System.out.println(h.apply("FF")); // FF -> 255 -> 11111
        System.out.println(h2.apply(2));  // 2 -> 10 -> 16

        Function<String, String> f2 = x -> x;  // 항등함수(identity function)5
        System.out.println(f2.apply("AAA"));

        Predicate<Integer> p = i -> i < 100;
        Predicate<Integer> q = i -> i < 200;
        Predicate<Integer> r = i -> i % 2 == 0;
        Predicate<Integer> notP = p.negate(); // i >= 100;

        Predicate<Integer> all = notP.and(q.or(r));  // -> i >= 100 && i < 200 || i % 2 = 0
        System.out.println(all.test(150));

        String str1 = "abc";
        String str2 = "abc";

        Predicate<String> p2 = Predicate.isEqual(str1);
        boolean result = p2.test(str2);
        System.out.println(result);


    }
}
