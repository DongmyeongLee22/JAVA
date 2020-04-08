package book.ch6;

import book.ch4.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class Ex3Test {
  public static void main(String[] args) {
    List<Dish> menu = Dish.makeDishes();
    String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining());

    String shortMenu2 = menu.stream().map(Dish::getName).collect(Collectors.reducing((s1, s2) -> s1 + s2)).get();

    String shortMenu3 = menu.stream().collect(Collectors.reducing("", Dish::getName, (s1, s2) -> s1 + s2));
  }
}
