package main.Lambda;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class 스트림의최종연산 {
    // 대표적으로 forEach

    public static void main(String[] args) {
        String[] strArr = {
                "AAA", "CCCC", "MASMD", "ASTewr",
                "GJGJG", "SADJQO", "WQDD", "QWJDOP"
        };

        Stream.of(strArr).forEach(System.out::println);

        boolean noneMatch = Stream.of(strArr).noneMatch(s -> s.length() == 0);
        System.out.println("noneMatch = " + noneMatch);

        Optional<String> first = Stream.of(strArr).filter(s -> s.charAt(0) == 'A').findFirst();
        System.out.println("first.get() = " + first.get());

        IntStream intStream1 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream2 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream3 = Stream.of(strArr).mapToInt(String::length);
        IntStream intStream4 = Stream.of(strArr).mapToInt(String::length);

        int count = intStream1.reduce(0, (a, b) -> a + 1);
        int sum = intStream2.reduce(0, (a, b) -> a + b);
        OptionalInt max = intStream3.reduce(Integer::max);
        OptionalInt min = intStream4.reduce(Integer::min);

        System.out.println("count = " + count);
        System.out.println("sum = " + sum);
        System.out.println("max.getAsInt() = " + max.getAsInt());
        System.out.println("min.getAsInt() = " + min.getAsInt());

    }

}
