package book.ch3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Ex4UtilComparator {
    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(
                new Apple(3, "A"),
                new Apple(7, "B"),
                new Apple(1, "A"),
                new Apple(2, "B")
        );

        // comparing으로 정렬을 간단하게 정의할 수 있다.
        Comparator<Apple> comparator = Comparator.comparing(Apple::getWeight);
        apples.sort(comparator);
        System.out.println(apples);

        // reversed를 통해 정렬을 뒤집을 수 있다.
        apples.sort(comparator.reversed());
        System.out.println(apples);

        // 같을 경우 추가 정렬 조건 적용 가능
        apples.sort(comparator.thenComparing(Apple::getType));
        System.out.println(apples);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    static class Apple {
        int weight;
        String type;
    }
}
