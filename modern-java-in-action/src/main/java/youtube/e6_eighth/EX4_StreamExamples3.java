package youtube.e6_eighth;

import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by Stranger on 2020/02/23
 */

/**
 * 인텔리J에서 Optimize imports를 통해 *로 임포트하는 기능을 설정하였다
 */
public class EX4_StreamExamples3 {

    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5)
                .collect(toList());

        Stream.of(1, 2, 3, 4, 5)
                .collect(toSet());

        Stream.of(1, 2, 3, 4, 5)
                .map(String::valueOf)
                .collect(joining());
    }
}
