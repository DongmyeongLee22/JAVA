package youtube.e6_stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * Created by Stranger on 2020/02/23
 */

/**
 * 람다로 만들었던 Product 계산들을 Stream으로 해보기
 */
public class EX5_StreamExamples4 {

    public static void main(String[] args) {
        Product productA = new Product(1L, "A", new BigDecimal("10.00"));
        Product productB = new Product(2L, "B", new BigDecimal("55.50"));
        Product productC = new Product(3L, "C", new BigDecimal("120.00"));
        Product productD = new Product(4L, "D", new BigDecimal("33.00"));
        Product productE = new Product(5L, "E", new BigDecimal("27.00"));

        final List<Product> products = Arrays.asList(
                productA,
                productB,
                productC,
                productD,
                productE
        );

        final BigDecimal thirtyDollar = new BigDecimal("30");

        System.out.println("Product.price >= 30: " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(thirtyDollar) >= 0)
                        .collect(toList()));

        System.out.println("\n==========================================");

        System.out.println("Product.price >= 30: (with joining) \n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(thirtyDollar) >= 0)
                        .map(Product::toString)
                        .collect(joining("\n")));

        System.out.println("\n==========================================");
        System.out.println("IntStream.sum: " +
                IntStream.of(1, 2, 3, 4, 5).sum()
        );

        System.out.println("\n==========================================");
        System.out.println("Total Price: " +
                        products.stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2)) // 요소를 하나씩 줄여가며 하나만 남긴다.
                // reduce : 초기값 , BiFunction(이전값, 다음값)

                // reduce(BigDecimal, (Product1, Product2)이다. 초기값인 BigDecimal이 Product1자리에 가야하는데 타입이 맞지 않아 불가능하다.
                // 뿐만아니라 리턴된 값이 또 Product1로 가야하지만 BigDecimal이므로 불가능하다.
                //.reduce(BigDecimal.ZERO, (p1, p2) -> p1.getPrice().add(p2.getPrice()))
        );

        // 이렇게하면 map을 안해도 되지만 map이 더 가독성이 좋은것을 알 수 있다.
//        System.out.println(products.stream()
//                .reduce(BigDecimal.ZERO,
//                        (price, product) -> price.add(product.getPrice()),
//                        (price1, price2) -> price1.add(price2)
//                ));

        System.out.println("\n==========================================");
        System.out.println("Total Price of Products.price >= 30: " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(thirtyDollar) >= 0)
                        .map(product -> product.getPrice())
                        .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println("\n==========================================");
        System.out.println("# of Products.price >= 30: " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(thirtyDollar) >= 0)
                        .count()
        );

        final OrderedItem item1 = new OrderedItem(1L, productA, 1);
        final OrderedItem item2 = new OrderedItem(2L, productB, 5);
        final OrderedItem item3 = new OrderedItem(3L, productC, 3);

        final Order order = new Order(1L, Arrays.asList(item1, item2, item3));
        System.out.println("\n==========================================");
        System.out.println("order.totalPrice(): " + order.totalPrice());
    }
    // 커맨드 F12 클래스 정보 보기
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

        public BigDecimal getTotalPrice() {
            return product.getPrice().multiply(new BigDecimal(this.quantity));
        }
    }

    @AllArgsConstructor
    @Data
    static class Order {
        private Long id;
        private List<OrderedItem> items;

        public BigDecimal totalPrice() {
            return items.stream()
                    .map(OrderedItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

        }
    }

}
