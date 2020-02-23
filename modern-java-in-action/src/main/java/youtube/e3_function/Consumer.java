package youtube.e3_function;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by Stranger on 2020/02/22
 */

/**
 * Supplier의 지연로딩의 효과를 알아보았다.
 */
public class Consumer {


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        printIfValidIndex(1, getVeryExpensiveValue());
        printIfValidIndex(-1, getVeryExpensiveValue());
        printIfValidIndex(-2, getVeryExpensiveValue());
        System.out.println("It took " + ((System.currentTimeMillis() - start) / 1000) + "second");
        // 여기서는 -1, -2에서는 value를 쓰지 않지만 사용되어 시간이 소모된다.

        start = System.currentTimeMillis();
        printIfValidIndex(1, () -> getVeryExpensiveValue());
        printIfValidIndex(-1, () -> getVeryExpensiveValue());
        printIfValidIndex(-2, () -> getVeryExpensiveValue());
        System.out.println("It took " + ((System.currentTimeMillis() - start) / 1000) + "second");
        // Supplier를 사용하면 지연 로딩이 되기 때문에 불필요한 계산을 줄여준다.

    }

    private static String getVeryExpensiveValue() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Dongmyeong";
    }

    private static void printIfValidIndex(int num, String val) {
        if (num >= 0) {
            System.out.println("This value is " + val);
        } else {
            System.out.println("Invalid");
        }
    }

    private static void printIfValidIndex(int num, Supplier<String> valSupplier) {
        if (num >= 0) {
            System.out.println("This value is " + valSupplier.get());
        } else {
            System.out.println("Invalid");
        }
    }
}
