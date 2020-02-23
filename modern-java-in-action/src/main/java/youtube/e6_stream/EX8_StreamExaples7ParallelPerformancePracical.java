package youtube.e6_stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Stranger on 2020/02/23
 */
public class EX8_StreamExaples7ParallelPerformancePracical {
    private static final String[] priceStrings = {"1.0", "100.99", "35.75", "21.30", "88.00"};
    private static final BigDecimal[] targetPrices = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("31")};
    private static final Random targetPriceRandom = new Random(111);
    private static final Random random = new Random(123);
    private static final List<Product> products;

    static {
        final int length = 8_000_000;
        final List<Product> list = new ArrayList<>(length);

        for(int i = 1; i <= length; i++){
            list.add(new Product((long) i, "Product" +i, new BigDecimal(priceStrings[random.nextInt(5)])));
        }

        products = Collections.unmodifiableList(list);
    }

    private static BigDecimal imperativeSum(final List<Product> products, final Predicate<Product> predicate){
        BigDecimal sum = BigDecimal.ZERO;
        for(final Product product : products){
            sum = sum.add(product.getPrice());
        }
        return sum;
    }
    private static BigDecimal streamSum(final Stream<Product> stream, final Predicate<Product> predicate){
        return stream.filter(predicate).map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void imperativeTest(BigDecimal targetPrice){
        System.out.println("====================================================");
        System.out.println("\nImperative Sum\n----------------------------------");
        final long start = System.currentTimeMillis();
        System.out.println("Sum: " +
                imperativeSum(products, product -> product.getPrice().compareTo(targetPrice) >= 0)
        );
        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms.");
        System.out.println("====================================================");
    }
    private static void streamTest(BigDecimal targetPrice){
        System.out.println("====================================================");
        System.out.println("\nStream Sum\n----------------------------------");
        final long start = System.currentTimeMillis();
        System.out.println("Sum: " +
                streamSum(products.stream(), product -> product.getPrice().compareTo(targetPrice) >= 0)
        );
        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms.");
        System.out.println("====================================================");
    }

    private static void parallelStreamTest(BigDecimal targetPrice){
        System.out.println("====================================================");
        System.out.println("\nParallel Stream Sum\n----------------------------------");
        final long start = System.currentTimeMillis();
        System.out.println("Sum: " +
                streamSum(products.parallelStream(), product -> product.getPrice().compareTo(targetPrice) >= 0)
        );
        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms.");
        System.out.println("====================================================");
    }
    public static void main(String[] args) {
        BigDecimal targetPrice = new BigDecimal("0");

        imperativeTest(targetPrice);
        streamTest(targetPrice);
        parallelStreamTest(targetPrice);

        System.out.println("\n Ignore Tests Above\n=============================================\n");
        System.out.println("Start!");
        for(int i = 0; i < 5; i++){
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>  Price: " + price.toString() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            /**
             * parallelStreamTest > imperativeTest > streamTest 순으로 속도가 빠른것을 알 수 있다.
             * - 단순 계산은 imperative가 빠를지 몰라도 시간이 걸릴 수족 parallelStream이 빠른것을 알 수 있다.
             * - 상황에 맞게 parallel을 쓸지 imperative를 쓸지 적절히 정해서 사용하는것이 좋다.
             * - 그러므로 사용하기전에 테스트를 해보고 사용해보자.
             */
            streamTest(price);
            parallelStreamTest(price);
            imperativeTest(price);
        }
    }


    @AllArgsConstructor
    @Data
    static class Product {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @AllArgsConstructor
    @Data
    static class OrderedItem {
        private Long id;
        private Product product;
        private int quantity;

    }

    @AllArgsConstructor
    @Data
    static class Order {
        private Long id;
        private List<OrderedItem> items;
    }
}

