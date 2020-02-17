package main.Lambda;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/*
    하나의 메서드만 호출하는 람다식은 '클래스이름::메서드이름'또는 '참조변수::메서드이름' 이름으로 바꿀 수 있다.

 */
public class 메서드참조 {

    Function<String, Integer> f = s -> Integer.parseInt(s);

    Function<String, Integer> f2 = Integer::parseInt;

    BiFunction<String, String, Boolean> b = (s1, s2) -> s1.equals(s2);

    BiFunction<String, String, Boolean> b2 = String::equals;

    Supplier<MyClass> s = () -> new MyClass();

    Supplier<MyClass> s1 = MyClass::new;

    Function<Integer, MyClass> q = (i) -> new MyClass(i);

    Function<Integer, MyClass> q2 = MyClass::new;

    BiFunction<Integer, String, MyClass> bf = (i, s) -> new MyClass(i, s);

    BiFunction<Integer, String, MyClass> bf2 = MyClass::new;

    Function<Integer, int[]> arr = x -> new int[x];

    Function<Integer, int[]> arr2 = int[]::new;


}

class MyClass {
    int i;
    String s;

    public MyClass(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public MyClass(int i) {
        this.i = i;
    }

    public MyClass() {
    }

}
