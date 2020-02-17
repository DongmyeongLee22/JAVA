package main.Lambda;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamEx5 {

    public static void main(String[] args) {
        Stream<String[]> strArrStr = Stream.of(
                new String[]{"abc", "def", "ghi"},
                new String[]{"ABC", "DEF", "GHI"}
        );

        // 이렇게 바꿀 수 있다.
        Stream<Stream<String>> streamStream = strArrStr.map(Arrays::stream);

        // 혹은 다 String으로 분리 가능
        //Stream<String> strStream = strArrStr.flatMap(Arrays::stream);

        String[] lineArr = {
                "Belive or net It is true",
                "Do or do not There is no try"
        };

        Stream<String> lineStream = Arrays.stream(lineArr);
        lineStream.flatMap(line -> Stream.of(line.split(" +")))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        Stream<String> strStream1 = Stream.of("AAA", "BBB");
        Stream<String> strStream2 = Stream.of("aaa", "bbb");

        Stream<Stream<String>> strStream11 = Stream.of(strStream1, strStream2);
        strStream11.map(s -> s.toArray(String[]::new))
                .flatMap(Arrays::stream)
                .map(String::toUpperCase)
                .distinct()
                .forEach(System.out::println);
    }
}
