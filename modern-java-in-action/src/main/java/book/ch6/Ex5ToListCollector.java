package book.ch6;

import book.ch4.Dish;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class Ex5ToListCollector<T> implements Collector<T, List<T>, List<T>> {

  // 누적자를 지정
  @Override
  public Supplier<List<T>> supplier() {
    return ArrayList::new;
  }

  // 누적에게 요소를 추가하는 연산
  @Override
  public BiConsumer<List<T>, T> accumulator() {
    return List::add;
  }

  // 병렬 처리시 결과를 처리할 때 사용(두 결과를 결합하기 위해)할 연산
  @Override
  public BinaryOperator<List<T>> combiner() {
    return (list1, list2) -> {
      list1.addAll(list2);
      return list1;
    };
  }

  // 최종 결과를 반활하기 위한 Function(현재는 누적자와 똑같은 List를 반환하므로 항등함수 사용)
  @Override
  public Function<List<T>, List<T>> finisher() {
    return Function.identity();
  }

  // 병렬, 리듀싱에 대한 최적화 힌트를 제공해줌.
  @Override
  public Set<Characteristics> characteristics() {
    return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
  }

  public static void main(String[] args) {
    List<Dish> menu = Dish.makeDishes();

    // 직접 만든 ToListColloctor를 이용하여 toList와 같은 기능을 구현할 수 있게 되었다.
    List<Dish> vegitarianDishes = menu.stream()
                                      .filter(Dish::isVegitarian)
                                      .collect(new Ex5ToListCollector<>());

    // IDENTITY_FINISH라면 굳이 Collector를 구현하지 않고도 만들 수 있다.
    List<Dish> vegitarianDishes2 = menu.stream()
                                       .filter(Dish::isVegitarian)
                                       .collect(ArrayList::new, // 누적자 초기 값
                                                 List::add, // 누적
                                                 List::addAll); // 합침
  }
}
