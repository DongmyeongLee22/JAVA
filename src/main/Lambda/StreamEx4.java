package main.Lambda;

import java.io.File;
import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
    스트림 값에서 특정 값을 뽑아내보자
    map 이용
 */
public class StreamEx4 {
    public static void main(String[] args) {
        Stream<File> fileSteam = Stream.of(
                new File("Ex1.java"),
                new File("Ex1"),
                new File("Ex1.bak"),
                new File("Ex2.java"),
                new File("Ex1.txt")
        );

//        fileSteam.map(File::getName).forEach(System.out::println);
        fileSteam.map(File::getName)
                .filter(s -> s.indexOf('.') != -1)
                .map(s -> s.substring(s.indexOf('.') + 1))  // 확장명만 뽑기
                .map(String::toUpperCase)
                .distinct()
                .forEach(System.out::println);


        Stream<File> fileSteam2 = Stream.of(
                new File("Ex1.java"),
                new File("Ex1"),
                new File("Ex1.bak"),
                new File("Ex2.java"),
                new File("Ex1.txt")
        );

        // peek으로 조회 가능하다. peek은 스트림을 소모하지 않으므로 계속 사용 가능하다.
        fileSteam2.map(File::getName)
                .filter(s -> s.indexOf('.') != -1)
                .peek(s -> System.out.printf("filename = %s, ", s))
                .map(s -> s.substring(s.indexOf('.') + 1))
                .peek(s -> System.out.printf("extension = %s, ", s))
                .forEach(System.out::println);

        // mapToInt, mapToLong 등 IntStream으로 할 수도 있음
        // IntStream은 Stream이 지원하는 count 이외에도 sum, max등 다양한거 지원한다.

        new Random().ints(1, 46)
                .distinct().limit(6).sorted()
                .mapToObj(i -> i + ", ")
                .forEach(System.out::print);
        System.out.println();

        System.out.println("123456".chars().sum());

        "123456".chars().forEach(s -> System.out.print(s + ", "));
        System.out.println();

        "123456".chars().map(c -> c - '0').forEach(s -> System.out.print(s + ", "));

        // 이놈 사용하면 스트림을 또 생성 안하고 같은 스트림 사용 가능
        IntSummaryStatistics stat = IntStream.range(1, 10).summaryStatistics();
        System.out.println(stat.getCount());
        System.out.println(stat.getSum());
        System.out.println(stat.getMax());
    }
}
