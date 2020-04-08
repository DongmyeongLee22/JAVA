# Chapter 6. 스트림으로 데이터 수집
### 컬렉터란?
- Collector 인터페이스 구현은 스트림의 요소를 어떤식으로 도출할지 지정한다.
- Collectors에서 제공하는 메서드의 기능을 크게 3가지로 구분할 수 있다.

**스트림 요소를 하나의 값으로 리듀스하고 요약**
- 리스트에서 총합을 계산하는 등의 다양한 계싼을 수행할 수 있다.

**요소 그룹화**
- 다수준으로 그룹화가 가능다.

**요소 분할**


#### 1) 요약 연산
```java
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
```

#### 2) 문자열 연결
- joining을 이용하면 스트림 각 요소의 toString메서드를 호출하여 하나의 문자로 만들어준다.

```java
String shortMenuName = menu.stream()
                           .map(Dish::getName)
                           .collect(joining());

String commaMenu = menu.stream()
                       .map(Dish::getName)
                       .collect(joining(", ")); // 조인하는 두 요소 사이에 문자열 지정가능
```

#### 3) reducing
- 위의 연산들은 Collector.reducing으로 모두 구현이 가능하다.
- reducing은 보통 초기값을 가지도록 구현되지만 초기값 없이도 구현이 가능하다.


#### 4) collect, reduce
```java
List<Integer> toList = Arrays.asList(1, 2, 3, 4, 5, 6)
                                   .stream()
                                   .reduce(
                                     new ArraysList<Integer>(),
                                     (List<Integer> l, Integer e) -> {
                                       l.add(e);
                                       return l;
                                     },
                                     (List<Integer> l1, List<Integer> l2) -> {
                                       l1.addAll(l2);
                                       return l1;
                                     }
                                   );
```
- 이렇게 reduce를 이용하면 collect의 toList처럼 동작시키도록 만들 수 있다.
- collect 메서드는 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계된 메서드이고, reduce는 두 값을 하나로 도출하는 불변형 연산을 위해 설계된 메서드이다.
- 위의 코드는 reduce를 통해 초기값으로 지정된 List를 계속해서 변환시키므로 reduce를 잘못 활용한 에시이다.
- 이런식으로 사용하면 병렬 연산이 힘들어지고 매번 리스트를 만들기때문에 성능에도 문제가 있다.
