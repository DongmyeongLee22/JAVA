package book.ch3;

import java.util.function.Function;

public class Ex6UtilFunction {
    public static void main(String[] args) {
        Function<Integer, Integer> plus2 = i -> i + 2;
        Function<Integer, Integer> mul3 = i -> i * 3;

        Function<Integer, Integer> plus2AndMul3 = plus2.andThen(mul3);
        System.out.println(plus2AndMul3.apply(3));
        // ( 3 + 2 ) * 3 = 15;

        Function<Integer, Integer> mul3AndPlus2 = plus2.compose(mul3);
        System.out.println(mul3AndPlus2.apply(3));
        // ( 3 * 3 ) + 2 = 11;

        Function<Integer, Integer> mul5 = i -> i * 5;
    }
}
