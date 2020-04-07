package book.ch5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Ex8NullableStream {
    public static void main(String[] args) {
        String config = System.getProperty("config");

        Stream<String> configStream = config == null ? Stream.empty() : Stream.of(config);

        // 자바 9부터 nullableStream이 생김
        Stream<String> configStream2 = Stream.ofNullable(config);

        // nullable를 잘 활용하면 유용하게 사용가능해진다.
        Stream<String> properies = Stream.of("config", "user", "key")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));

        long uniqueWords = 0;
        try(Stream<String> lines = Files.lines(Paths.get("data.txt", String.valueOf(Charset.defaultCharset())))){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(""))).distinct().count();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
