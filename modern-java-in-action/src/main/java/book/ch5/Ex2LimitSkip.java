package book.ch5;

import book.ch4.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class Ex2LimitSkip {
    public static void main(String[] args) {
        List<Dish> menu = Dish.makeDishes();

        List<String> names = menu.stream()
                                 .map(Dish::getName)
                                 .limit(3) // 3개로 제한
                                 .collect(Collectors.toList());

        List<String> namesOneSkip = menu.stream()
                                        .map(Dish::getName)
                                        .skip(1)  // 하나를 건너 뜀
                                        .limit(3)
                                        .collect(Collectors.toList());

        System.out.println("names = " + names);
        System.out.println("namesOneSkip = " + namesOneSkip);

        List<Dish> twoMeats = menu.stream()
                                  .filter(dish -> !dish.isVegitarian())
                                  .limit(2)
                                  .collect(Collectors.toList());
    }
}
