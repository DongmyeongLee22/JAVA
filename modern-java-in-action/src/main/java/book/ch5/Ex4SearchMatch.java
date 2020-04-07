package book.ch5;

import book.ch4.Dish;

import java.util.List;
import java.util.Optional;

public class Ex4SearchMatch {
    public static void main(String[] args) {
        List<Dish> menu = Dish.makeDishes();

        // 하나만 매칭되면 true 반환
        boolean hasVegitarian = menu.stream().anyMatch(Dish::isVegitarian);

        // 모두 매칭되면 true 반환
        boolean allVegitarian = menu.stream().allMatch(Dish::isVegitarian);

        // 하나도 매칭안되면 true 반환
        boolean noneVergitarian = menu.stream().noneMatch(Dish::isVegitarian);

        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegitarian)
                .findAny(); // 스트림 중 임의의 요소를 하나 반환한다, 병렬이 아니라면 가장 먼저 만나는 것을 할 것이다.

        Optional<Dish> first = menu.stream()
                .filter(Dish::isVegitarian)
                .findFirst(); // 스트림 중 첫번째 요소를 반환한다.

    }
}
