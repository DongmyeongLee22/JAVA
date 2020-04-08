package book.ch6;

import book.ch4.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Ex2JoiningString {
  public static void main(String[] args) {
    List<Dish> menu = Dish.makeDishes();
    String shortMenuName = menu.stream()
      .map(Dish::getName)
      .collect(joining());

    String commaMenu = menu.stream()
      .map(Dish::getName)
      .collect(joining(", ")); // 조인하는 두 요소 사이에 문자열 지정가능

    List<Integer> toList = Arrays.asList(1, 2, 3, 4, 5, 6)
      .stream()
      .reduce(
        new ArrayList<Integer>(),
        (List<Integer> l, Integer e) -> {
          l.add(e);
          return l;
        },
        (List<Integer> l1, List<Integer> l2) -> {
          l1.addAll(l2);
          return l1;
        }
      );
  }
}
