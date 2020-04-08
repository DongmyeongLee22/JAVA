package book.ch6;

import book.ch4.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Ex4Grouping {
  public static void main(String[] args) {
    List<Dish> menu = Dish.makeDishes();

    Map<CaloricLevel, List<Dish>> disheByCaloricLevel
      = menu.stream()
            .collect(groupingBy(dish -> {
              if (dish.getCalories() <= 400) return CaloricLevel.DIET;
              else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
              else return CaloricLevel.FAT;
            }));

    // FISH는 KEY조차 존재하지 않을 것이다.
    Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                                                  .filter(dish -> dish.getCalories() > 500)
                                                  .collect(groupingBy(Dish::getType));

    // groupingBy에서 filtering을 사용하면 FISH KEY는 존재하게 된다.
    Map<Dish.Type, List<Dish>> dishesByType2 =
      menu.stream()
          .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));

    // mapping을 활용할 수 도 있다.
    Map<Dish.Type, List<String>> dishesByType3 =
      menu.stream()
          .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));

    Map<Dish.Type, Map<CaloricLevel, List<Dish>>> multipleGrouping =
      menu.stream()
          .collect(
                  groupingBy(Dish::getType,
                    groupingBy(dish -> {
                      if (dish.getCalories() <= 400)
                        return CaloricLevel.DIET;
                      else if (dish.getCalories() <= 700)
                        return CaloricLevel.NORMAL;
                      else
                        return CaloricLevel.FAT;
                    }))
                  );

    // 사실 이건 groupingBy(Dish::getType, toList())의 축양형이다.
    Map<Dish.Type, List<Dish>> dishesByType223 = menu.stream()
                                                  .collect(groupingBy(Dish::getType));

    // counting을 이용하면 Type별 카운트가 가능하다.
    Map<Dish.Type, Long> typesCount = menu.stream()
                                       .collect(groupingBy(Dish::getType, counting()));

    // maxBy같은 메서드도 존재하기 때문에 Type별 최대 칼로리를 가지는 Dish를 담을 수 있다.
    Map<Dish.Type, Optional<Dish>> typesMaxDish =
      menu.stream()
          .collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));

    // collectingAndThen을 활용하면 다른 결과를 가져오게할 수 있다. 여기선 Optinal.get을 통해 바로 DISH를 담았다.
    Map<Dish.Type, Dish> typesMaxDish2 =
      menu.stream()
          .collect(
            groupingBy(
                Dish::getType,
                collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));

    // 타입별 칼로리 합계
    Map<Dish.Type, Integer> typeBySumCalories =
      menu.stream()
          .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));


    // mapping을 통해 원하는 값으로 변환이 가능하다.
    // 타입별 존재하는 CaloricLevel들을 확인할 수 있을 것이다.
    Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
      menu.stream()
          .collect(
              groupingBy(Dish::getType, mapping(dish -> {
                if (dish.getCalories() <= 400)
                  return CaloricLevel.DIET;
                else if (dish.getCalories() <= 700)
                  return CaloricLevel.NORMAL;
                else
                  return CaloricLevel.FAT;
              },
                toSet())
              ));

    // 분할 함수(partitioningBy 메서드 사용)
    Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegitarian));

    // 채식 요리들 , 그냥 filter를 통해서 얻을 수 있겠지만 참, 거짓 두 가지 요소의 스트림 리스트를 모두 유지하기 때문에 활용도가 높다.
    List<Dish> vagetarianDishes = partitionedMenu.get(true);

    // 소수인 목록, 아닌 목록을 담은 분할맵
    Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(100);
  }

  static private boolean isPrime(int n){
    int sqrt = (int) Math.sqrt(n);
    return IntStream.rangeClosed(2, sqrt).noneMatch(i -> n % i == 0);
  }

  static public Map<Boolean, List<Integer>> partitionPrimes(int n){
    return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(Ex4Grouping::isPrime));
  }


  }

  enum CaloricLevel {DIET, NORMAL, FAT}

