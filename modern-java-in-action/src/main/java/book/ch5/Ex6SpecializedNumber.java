package book.ch5;

import book.ch4.Dish;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex6SpecializedNumber {
    public static void main(String[] args) {
        List<Dish> menu = Dish.makeDishes();

        int sumCalories = menu.stream()
                .mapToInt(Dish::getCalories) // IntStream으로 변환
                .sum();

        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> boxed = intStream.boxed(); // boxed로 다시 Stream으로 복원 가능하다.

OptionalInt maxOp = menu.stream().mapToInt(Dish::getCalories).max();
int max = maxOp.orElse(0);
    }
}
