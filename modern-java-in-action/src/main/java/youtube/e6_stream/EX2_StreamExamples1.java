package youtube.e6_stream;

import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Stranger on 2020/02/22
 */
public class EX2_StreamExamples1 {
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(System.out::println);


        IntStream.iterate(1, i -> i + 1)
                .forEach(System.out::println);

        // 무한 컬렉션
        Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
                .forEach(System.out::println);
    }
}
