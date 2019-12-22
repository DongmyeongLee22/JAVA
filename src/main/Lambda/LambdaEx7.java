package main.Lambda;

import javax.print.attribute.standard.Finishings;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


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

        /*
        하나의 메서드만 호출하는 람다식은 '클래스이름::메서드이름'또는 '참조변수::메서드이름' 이름으로 바꿀 수 있다.
        */
        // ======================= 메서드 참조 ============================
        Function<String, Integer> f3 = (s) -> Integer.parseInt(s);
        // 위의 내용을 아래와 같이 바꿀 수 있다.
        Function<String, Integer> f4 = Integer::parseInt;


        BiFunction<String, String, Boolean> f5 = (s1, s2) -> s1.equals(s2);
        // 위에서 s1, s2를 빼면 equals만 남는다 그래서 아래와 같이 사용 가능하다.
        BiFunction<String, String, Boolean> f6 = String::equals;

        MyClass obj = new MyClass();
        // 람다식
        Function<String, Boolean> f7 = (x) -> obj.equals(x);
        // 메서드 참조 -> 이미 생성된 객체의 메서드는 객체의 참조변수를 적어준다.
        Function<String, Boolean> f8 = obj::equals;


        // ======================= 생성자의 메서드 참조 ============================
        // 람다식
        Supplier<MyClass> ss = () -> new MyClass();
        // 메서드 참조
        Supplier<MyClass> s1 = MyClass::new;

        // 이렇게 매개변수가 있더라도 사용할 수 있다.
        Function<Integer, MyClass> s3 = (i) -> new MyClass(i);
        Function<Integer, MyClass> s4 = MyClass::new;

        BiFunction<Integer, String, MyClass> s5 = (i, s) -> new MyClass(i, s);
        BiFunction<Integer, String, MyClass> s6 = MyClass::new;


        // 배열 생성도 가능하다!!
        Function<Integer, int[]> s7 = x -> new int[x];
        Function<Integer, int[]> s8 = int[]::new;

    }
}
