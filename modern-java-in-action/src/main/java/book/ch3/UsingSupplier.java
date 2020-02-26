package book.ch3;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/25
 */
public class UsingSupplier {
    public static void main(String[] args) {
        final List<Integer> numberList = Arrays.asList(-3, -2, -1, 0, 1, 2, 3);

        final long start = System.currentTimeMillis();
        for (Integer integer : numberList) {
            printIfPositiveNumber2(integer, () -> complexOperation());
        }
        System.out.println("동작 시간: " + (System.currentTimeMillis() - start) / 1000 + "sec");
    }

private static void printIfPositiveNumber2(Integer integer, Supplier<String> supplier) {
    if (integer > 0) {
        System.out.println(supplier.get());
    }
}

    private static void printIfPositiveNumber(Integer integer, String string) {
        if (integer > 0) {
            System.out.println(string);
        }
    }

    private static String complexOperation() {
        try {
            // 복잡한 연산 후 String을 반환한다.
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, World!";
    }
}
