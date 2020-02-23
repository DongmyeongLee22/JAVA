package youtube.e9_method_reference;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Created by Stranger on 2020/02/23
 */
/*
    생성자, 배열 생성자도 Method Reference로 알아본다.
 */
public class EX2_MethodReferenceExamples2Constructor {
    public static void main(String[] args) {

        final Section section = new Section(1);

        final Function<Integer, Section> sectionFactoryWithLambdaExpression = integer -> new Section(integer);
        final Section sectionWithLambdaExpression = sectionFactoryWithLambdaExpression.apply(1);

        final Function<Integer, Section> sectionFactoryWithMethodReference = Section::new;
        final Section sectionWithMethodReference = sectionFactoryWithMethodReference.apply(1);

        System.out.println(section);
        System.out.println(sectionWithLambdaExpression);
        System.out.println(sectionWithMethodReference);

        System.out.println("\n================================================");

//        final OldProduct product = new OldProduct(1L, "A", new BigDecimal("100"));
//        final ProductCreator productCreator = OldProduct::new;
//        final OldProduct productWithMethodReference = productCreator.create(1L, "A", new BigDecimal("100"));
//        System.out.println(product);
//        System.out.println(productWithMethodReference);

        /* 왜 굳이 new 키워드를 사용하지 않고 Function을 이용하여 만들까?, 어떤 이점이 존재할까?
         *  - ProductA, B 참고
         *  - 상황에 따라 A, B를 생성할 때가 있을 때 function을 사용하면 유연하게 인스턴스를 생성할 수 있다.
         *  - function을 사용하지 않으면 if문으로 비교하여 생성해야 할 것이다.
         *  - 하지만 function을 이용하면 ProductC가 생기더라도 쉽게 확장시킬 수 있다.
         */
        final ProductA productA = createProduct(1L, "A", new BigDecimal("123"), ProductA::new);
        final ProductB productB = createProduct(1L, "A", new BigDecimal("123"), ProductB::new);
        System.out.println(productA);
        System.out.println(productB);
    }

    private static <T extends Product> T createProduct(final Long id,
                                                       final String name,
                                                       final BigDecimal price,
                                                       final ProductCreator<T> creator) {
        if (id == null || id < 1L) {
            throw new IllegalArgumentException("The id must be a positive Long.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name is not given.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be greater then 0.");
        }
        return creator.create(id, name, price);
    }
}

@FunctionalInterface
interface ProductCreator<T extends Product> {
    T create(Long id, String name, BigDecimal price);
}

@AllArgsConstructor
@Data
class Section {
    private int number;
}

@AllArgsConstructor
@Data
abstract class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

@AllArgsConstructor
@Data
class OldProduct {
    private Long id;
    private String name;
    private BigDecimal price;
}

class ProductA extends Product {
    public ProductA(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "A = " + super.toString();
    }
}

class ProductB extends Product {
    public ProductB(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "B = " + super.toString();
    }
}