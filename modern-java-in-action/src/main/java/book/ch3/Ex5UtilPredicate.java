package book.ch3;

import java.util.function.Predicate;

import static book.ch3.Ex4UtilComparator.*;

public class Ex5UtilPredicate {
public static void main(String[] args) {
    Predicate<Apple> isAType = a -> "A".equals(a.getType());

    // negate로 조건의 반대를 적용할 수 있음
    Predicate<Apple> isNotAType = isAType.negate();

    // (isATupe && weight > 10) || (weight < 3)
    Predicate<Apple> complexPredicate = isAType.and(a -> a.getWeight() > 10)
                                               .or(a -> a.getWeight() < 3);
}
}
