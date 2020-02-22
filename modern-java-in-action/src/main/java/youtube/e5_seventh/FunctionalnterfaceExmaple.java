package youtube.e5_seventh;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Stranger on 2020/02/22
 */

/**
 * 1. 제품들을 추가한 리스트를 만듬
 * 2. 해당 리스트를 filter하여 50 이상의 값만 가져온다.
 * 3. 해당 값들을 50% 할인 시킨다.
 * 4. 각 값들의 total을 구해본다.
 * 5. 할인된 값에서 50 이상의 값만 구해본다.
 * 6. Order를 만들어 다른 객체들로도 할 수 있는 것을 알아본다.
 */
public class FunctionalnterfaceExmaple {
    public static void main(String[] args) {
        Product productA = new Product(1L, "A", new BigDecimal("10.00"));
        Product productB = new Product(2L, "B", new BigDecimal("55.50"));
        Product productC = new Product(3L, "C", new BigDecimal("120.00"));
        Product productD = new Product(4L, "D", new BigDecimal("33.00"));
        Product productE = new Product(5L, "E", new BigDecimal("27.00"));

        // 1
        final List<Product> products =
                List.of(productA, productB, productC, productD, productE);

        // 2
        List<Product> expensiveProducts =
                filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);

//        List<DiscountedProduct> discountedProducts = new ArrayList<>();
//        expensiveProducts.forEach(product ->
//                map(product, p ->
//                        discountedProducts.add(
//                                new DiscountedProduct(
//                                        p.getId(), p.getName(), p.getPrice().multiply(new BigDecimal("0.5"))
//                                )
//                        )
//                )
//        );

        // 3
        Function<Product, DiscountedProduct> mapToDiscountProduct =
                p -> new DiscountedProduct(p.getId(), p.getName(),p.getPrice().multiply(new BigDecimal("0.5")));
        List<DiscountedProduct> discountedProducts = map(expensiveProducts, mapToDiscountProduct);

        // 4
        System.out.println("Expensive Products " + expensiveProducts);
        System.out.println("Discount Products " + discountedProducts);

        // 5
        System.out.println("Filtered Discount Products( > 50) " + filter(
                discountedProducts, dp -> dp.getPrice().compareTo(new BigDecimal("50")) > 0
        ));
        System.out.println("Expensive Total = " + total(expensiveProducts, Product::getPrice));

        // 6
        Order order = new Order(1L, "on-123", List.of(
                new OrderedItem(1L, productA, 2),
                new OrderedItem(2L, productC, 1),
                new OrderedItem(3L, productD, 10)
        ));
        System.out.println("Order Total = " + total(order.getItems(),
                od -> od.getProduct().getPrice().multiply(BigDecimal.valueOf(od.getQuantity()))));
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> ret = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) ret.add(t);
        }
        return ret;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> fc){
        final List<R> ret = new ArrayList<>();
        for (T t : list) {
            ret.add(fc.apply(t));
        }
        return ret;
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> fn){
        BigDecimal total = BigDecimal.ZERO;
        for (T t : list) {
            total = total.add(fn.apply(t));
        }
        return total;
    }

}


@AllArgsConstructor
@Data
class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

class DiscountedProduct extends Product {

    public DiscountedProduct(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }
}

@AllArgsConstructor
@Data
class OrderedItem{
    private Long id;
    private Product product;
    private int quantity;
}

@AllArgsConstructor
@Data
class Order {
    private Long id;
    private String orderNumber;
    private List<OrderedItem> items;
}