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


#### 4) collect와 reduce
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

### 그룹화
- groupingBy를 통해 간단하게 그룹화시킬 수 있다.

```java
Map<Dish.Type, List<Dish>> disheByType = menu.stream().collect(groupingBy(Dish::getType));
```
- 이를 잘 활용하면 아래와 같은 그룹화도할 수 있다.


```java
public static void main(String[] args) {
    List<Dish> menu = Dish.makeDishes();

    // enum으로 Level을 지정하여 그루핑할 수 있다.
    Map<CaloricLevel, List<Dish>> disheByCaloricLevel
      = menu.stream()
            .collect(groupingBy(dish -> {
              if (dish.getCalories() <= 400) return CaloricLevel.DIET;
              else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
              else return CaloricLevel.FAT;
            }));
  }

enum CaloricLevel {DIET, NORMAL, FAT}
```

#### 1) 그룹화된 요소 조작
```java
new Dish("port", false, 800, Type.MEAT),
new Dish("beef", false, 700, Type.MEAT),
new Dish("chicken", false, 400, Type.MEAT),
new Dish("french", false, 530, Type.OTHER),
new Dish("rice", true, 350, Type.OTHER),
new Dish("season", true, 120, Type.OTHER),
new Dish("pizza", true, 550, Type.OTHER),
new Dish("prawns", false, 300, Type.FIST),
new Dish("slamon", false, 450, Type.FIST)
```
- 이러한 메뉴가 있을 때 아래와같이 그루핑할 수 있다.

```java
// FISH는 KEY조차 존재하지 않을 것이다.
Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                                              .filter(dish -> dish.getCalories() > 500)
                                              .collect(groupingBy(Dish::getType));

// groupingBy에서 filtering을 사용하면 FISH KEY는 존재하게 된다.
Map<Dish.Type, List<Dish>> dishesByType2 =
  menu.stream()
      .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
```

#### 2) 다수준 그룹화
```java
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
```
- grouping을 연달아 사용하면 다수준으로 그룹화할 수 있다.


#### 3) 그룹화로 데이터 수집
```java
// 사실 이건 groupingBy(Dish::getType, toList())의 축양형이다.
Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
                                              .collect(groupingBy(Dish::getType));

// counting을 이용하면 Type별 카운트가 가능하다.
Map<Dish.Type, Long> typesCount = menu.stream()
                                   .collect(groupingBy(Dish::getType, counting()));

// maxBy같은 메서드도 존재하기 때문에 Type별 최대 칼로리를 가지는 Dish를 담을 수 있다.
Map<Dish.Type, Optional<Dish>> typesMaxDish =
  menu.stream()
      .collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));

// collectingAndThen을 활용하면 다른 결과를 가져오게할 수 있다. 여기선 Optinal.get을 통해 바로 DISH를 담았다.
// 리듀싱은 절때 empty를 반환하지 않으므로 바로 .get하여도 문제 없다.
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
```
- 다양한 방식의 데이터 수집 메서드들을 제공한다.


### 분할
- 분할 함수는 Predicate를 통해 분류하여 그룹화하는 기능이다. 그러므로 맵의 키는 Boolean이 된다.

```java
public static void main(String[] args) {
  List<Dish> menu = Dish.makeDishes();

  // 분할 함수(partitioningBy 메서드 사용)
  Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegitarian));

  // 채식 요리들 , 그냥 filter를 통해서 얻을 수 있겠지만 참, 거짓 두 가지 요소의 스트림 리스트를 모두 유지하기 때문에 활용도가 높다.
  List<Dish> vagetarianDishes = partitionedMenu.get(true);

  // 소수인 목록, 아닌 목록을 담은 분할맵
  Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(100);
}

// ==== 소수 분할 메서드들 ===
static private boolean isPrime(int n){
  int sqrt = (int) Math.sqrt(n);
  return IntStream.rangeClosed(2, sqrt).noneMatch(i -> n % i == 0);
}

static public Map<Boolean, List<Integer>> partitionPrimes(int n){
  return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(Ex4Grouping::isPrime));
}
```

### toList() 기능 구현해보기

```java
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
```


### 소수 확인 Collector 구현
- 사실 소수인지를 확인하려면 이전의 소수들에 대한 나머지가 0인 경우만 찾으면 된다.
- 하지만 위에서 사용한 방법은 모든 수를 확인하기 때문에 비효율적이다.
- 이는 Collector를 구현하여 해결할 수 있다.
- [코드 확인](Ex6CustomCollector.java)
- 따로 Collector를 구현하지 않고 collect에 직접 사용할 수 도 있다.

```java
static public Map<Boolean, List<Integer>> partitionPrimes(int n) {
  return IntStream.rangeClosed(2, n)
    .boxed()
    .collect(
      // 누적자 초기화
      () -> new HashMap<>() {
        {
          put(true, new ArrayList<>());
          put(false, new ArrayList<>());
        }
      },
      // 누적자와 요소를 이용하여 값 추가
      (acc, candidate) -> acc.get(isPrime3(acc.get(true), candidate)).add(candidate),
      // 병렬 처리시 필요한 합치기
      (map1, map2) -> {
        map1.get(true).addAll(map2.get(true));
        map1.get(false).addAll(map2.get(false));
      }
    );
}
```
- 한번만 사용이 필요하다면 간단하게 collect안에서 구현할 수 있겠지만 재사용성과 가독성을 위해서라면 Collector를 구현하여 사용하는게 좋을 것이다.
