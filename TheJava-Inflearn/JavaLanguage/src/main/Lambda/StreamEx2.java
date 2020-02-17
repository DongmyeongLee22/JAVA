package main.Lambda;


import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamEx2 {
    public static void main(String[] args) {
        IntStream.range(1, 10).forEach(System.out::print);
        System.out.println();

        IntStream.rangeClosed(1, 10).forEach(System.out::print);
        System.out.println();
        // skip, limit 이용 3개는 건너뛰고 5개로 갯수를 제한한다.
        IntStream.rangeClosed(1, 10).skip(3).limit(5).forEach(System.out::print);
        System.out.println();

        // 중복 제거
        IntStream.of(1, 1, 1, 1, 2, 2, 2, 2).distinct().forEach(System.out::print);
        System.out.println();

        // 필터는 매개변수로 Predicate를 필요로하나 람다식으로 boolean 리턴하게 하면 된다.
        // 여러 필터 연속 사용 가능
        IntStream.rangeClosed(1, 10).filter(i -> i % 2 == 0).forEach(System.out::print);
        System.out.println();

        IntStream.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).sorted().forEach(System.out::print);
        System.out.println();

        // 그냥 Stream은 Comparator 설정 가능
        Stream.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).sorted((a, b) -> b - a).forEach(System.out::print);
        System.out.println();

        Stream.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).sorted(Comparator.reverseOrder()).forEach(System.out::print);
        System.out.println();

        Stream.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).sorted(Comparator.naturalOrder()).forEach(System.out::print);
        System.out.println();

        Stream.of("dd", "aa", "cc").sorted(String::compareTo).forEach(System.out::print);
        System.out.println();

        Stream.of("ddc", "aaaa", "cc").sorted(Comparator.comparing(String::length)).forEach(System.out::print);


    }
}
