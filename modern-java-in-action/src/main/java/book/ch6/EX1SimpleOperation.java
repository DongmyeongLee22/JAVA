package book.ch6;

import book.ch4.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class EX1SimpleOperation {
    public static void main(String[] args) {
        List<Dish> menu = Dish.makeDishes();


        // couting을 이용하면 count가능
        long count = menu.stream()
                         .filter(Dish::isVegitarian)
                         .collect(counting());

        // 위를 간단하게 표현할 수 있다.
        long count2 = menu.stream()
                          .filter(Dish::isVegitarian)
                          .count();


        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);

        // maxBy(comparator)를 이용하여 max계산 가능
        Optional<Dish> maxCalroriesMenu = menu.stream()
                                              .collect(maxBy(dishComparator));
        // 위를 간단하게 표현할 수 있다.
        Optional<Dish> maxCalroriesMenu2 = menu.stream().max(dishComparator);

        // summingInt, averagingInt등을 통해 sum, avg를 계산할 수 있다.
        int sum1 = menu.stream().collect(summingInt(Dish::getCalories));
        // 위를 간단히 표현
        int sum2 = menu.stream().mapToInt(Dish::getCalories).sum();

        double avg1 = menu.stream().collect(averagingInt(Dish::getCalories));
    }
}
