package book.ch5;

import book.ch4.Dish;

import java.util.List;

import static java.util.stream.Collectors.*;

public class EX1 {
    public static void main(String[] args) {
        List<Dish> menu = Dish.makeDishes();

        List<Dish> filteredMenu = menu.stream()
                                      .filter(dish -> {
                                          System.out.println("name:: " + dish.getName());
                                          return dish.getCalories() < 400;
                                      })
                                      .collect(toList());
        System.out.println("filteredMenu = " + filteredMenu);
        System.out.println("======================");
        List<Dish> slicedMenu = menu.stream()
                                    .takeWhile(dish -> {
                                        System.out.println("name:: " + dish.getName());
                                        return dish.getCalories() < 400;
                                    })
                                    .collect(toList());
        System.out.println("slicedMenu = " + slicedMenu);
        System.out.println("======================");

        List<Dish> slicedMenu2 = menu.stream()
                                     .dropWhile(dish -> {
                                        System.out.println("name:: " + dish.getName());
                                        return dish.getCalories() < 400;
                                     })
                                     .collect(toList());
        System.out.println("slicedMenu2 = " + slicedMenu2);
    }
}
