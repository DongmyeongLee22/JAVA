package book.ch5;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Ex9IterateStream {
    public static void main(String[] args) {
//        Stream.iterate(0, n -> n + 2)
//                .forEach(i -> {
//                    try {
//                        TimeUnit.MICROSECONDS.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(i);
//                });

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1] , t[0] + t[1]})
                .limit(30)
                .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

Random random = new Random();
Stream.generate(() -> random.nextInt(30))
        .distinct()
        .limit(10)
        .forEach(i -> System.out.print(i + " "));
    }
}
