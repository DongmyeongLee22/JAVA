package book.ch5;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex7Pythagorean {
    public static void main(String[] args) {
        Stream<int[]> pair = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new int[]{i, i + 1});
    }
}
