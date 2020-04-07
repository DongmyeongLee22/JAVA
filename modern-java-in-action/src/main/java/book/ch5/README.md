# Chapter 5. 스트림 활용
### 필터링
- 가장 단순하게 Predicate를 이용한 filter로 필터할 수 있다.
- 그리고 distinct로 중복을 제거할 수 있다.

### 스트림 축소과 건너띄기
```java
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
}
```
- limit으로 개수를 제한하고 skip으로 요소를 건너띌 수 있다.



### 맵
#### 1) FlatMap
```java
public static void main(String[] args) {
        List<String> strings = Arrays.asList("Hello", "World");

        List<String[]> collect = strings.stream()
                                        .map(word -> word.split(""))
                                        .distinct()
                                        .collect(Collectors.toList());
    }
}
```
- Hello, World라는 단어를 하나씩 알파벳으로 쪼갠 후 중복을 제거하도록할 때 위와 같이 구현한다면 String배열이 담기므로 제대로 구현이 되지 않을 것이다.
- map을 한번 더 해서 아래와 같이 하더라고 List<Stream<String>>이 된다.

```java
List<Stream<String>> collect = strings.stream()
                                      .map(word -> word.split(""))
                                      .map(Arrays::stream)
                                      .distinct()
                                      .collect(Collectors.toList());
```
- 이럴 때 flatMap을 사용하면 된다.

```java
List<String> collect = strings.stream()
                                      .map(word -> word.split(""))
                                      .flatMap(Arrays::stream)
                                      .distinct()
                                      .collect(Collectors.toList());
```
- flatMap은 각 배열을 스트림의 콘텐츠로 매핑하게 된다.


#### 2) 예제
- 만약 {1, 2, 3}, {3, 4}의 리스트가 있을 때 모든 숫자의 쌍을 반환하기 위해선?

```java
List<Integer> numbers1 = Arrays.asList(1, 2, 3);
List<Integer> numbers2 = Arrays.asList(3, 4);

List<int[]> pairs = numbers1.stream()
                              .flatMap(number1 -> numbers2.stream()
                                                          .map(number2 -> new int[]{number1, number2})
                              )
                              .collect(Collectors.toList());
```

### 검색과 매칭
#### 1) 매칭
```java
public static void main(String[] args) {
    List<Dish> menu = Dish.makeDishes();

    // 하나만 매칭되면 true 반환
    boolean hasVegitarian = menu.stream().anyMatch(Dish::isVegitarian);

    // 모두 매칭되면 true 반환
    boolean allVegitarian = menu.stream().allMatch(Dish::isVegitarian);

    // 하나도 매칭안되면 true 반환
    boolean noneVergitarian = menu.stream().noneMatch(Dish::isVegitarian);

}
```
- 위 메서드 모두 쇼트서킷 기법을 사용하므로 &&, || 연산자를 통해 구현된다.
- 그렇기 때문에 모든 연산을 처리하지 않고 앞에 연산만으로도 결과가 도출할 수 있다면 결과를 도출할 것이다.(이를 쇼트 서킷이라고 한다.)


#### 2) 검색
```java
Optional<Dish> dish = menu.stream()
                          .filter(Dish::isVegitarian)
                          .findAny(); // 스트림 중 임의의 요소를 하나 반환한다, 병렬이 아니라면 가장 먼저 만나는 것을 할 것이다.

Optional<Dish> first = menu.stream()
                           .filter(Dish::isVegitarian)
                           .findFirst(); // 스트림 중 첫번째 요소를 반환한다.
```
- findFirst의 경우 병렬로 구성할 경우 첫 번째 요소를 찾기 어려우니 순서가 상관없다면 findAny가 좋을 것이다.


### 리듀싱
- 어떤 Integer 스트림의 총 합계를 구하기 위해서는 Integer 결과가 나올 때 까지 스트림의 모든 요소를 처리해야할 것이다.
- 이런식으로 모든 스트림의 요소를 처리해서 값으로 도출하는 것을 **리듀싱 연산** 이라고 한다.
- 함수형 프로그래밍 언어에서는 스트림을 계속해서 접어 하나로 만드는 거와같은 의미로 **폴드** 라고도 한다.

```java
public static void main(String[] args) {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);


    // ======= 일반 for문 =======
    int sum = 0; // 초기 값
    for (Integer number : numbers) {
        sum += number; // 연산
    }

    // ======= 리듀싱 연산 ======= : 동일하게 초기값과 연산 과정을 가지고 있다.
    Integer sum2 = numbers.stream().reduce(0, (pre, cur) -> pre + cur);
    // pre + cur의 연산 결과가 다시 pre가 되는 식으로 계속해서 연산이 동작된다.
}
```
- 이렇게 초기값을 이용하여 리듀스를 할 수 잇지만 초기값이 없을 때도 있다.
- 그렬경우는 아래와 같이 값이 존재하지 않을 수 있으므로 Optional을 반환하는 것을 알 수 있다.

```java
Optional<Integer> sum3Op = numbers.stream().reduce((pre, cur) -> pre + cur);

// 최소 최댓값도 간단히 구할 수 있다.
Integer min = numbers.stream().reduce(Math::min).get();
Integer max = numbers.stream().reduce(Math::max).get();
```

#### 1) reduce 메서드의 장점과 병렬화
- 이런 연산들의 경우 일반 반복문을 이용할 때 병렬화를 수행하려면 sum 변수를 공유해야 하는데 이는 간단하지가 않다.
- 하지만 reduce를 사용하면 단지 stream()을 parallelStream()으로 변경하기만 하면된다.

#### 2) 스트림의 상태(상태 없음, 있음)
- map, filter 등은 입력에서 요소를 받아 결과를 출력하는 메서드들이다.
- 메서드 안에 정의하는 람다식이나 메서드 참조에 별다른 참조를 하지 않는 이상 이는 상태를 가지지 않기때문에 **내부 상태를 갖지 않는 연산이다.**
- 하지만 reduce의 경우 결과를 누적할 내부 상태가 필요하다.
- 그리고 sorted, distinct와 같은 연산들은 과거의 이력을 알고 있어야 구현이 되므로 모든 요소가 버퍼에 추가되어 있어야 한다.
- 만약 무한 스트림일 때 이러한 연산을 수행한다면 문제가 생길 수 있을 것이다.
- 이러한 연산들을 **내부 상태를 가지는 연산이라고 한다.**

### 숫자형 스트림
- 스트림으로 숫자형을 구현하면 Integer와같이 박싱된 형태로 구성되므로 비용이 든다.
- 그러므로 자바는 int, logn등에 특화된 숫자형 스트림을 제공한다.

```java
List<Dish> menu = Dish.makeDishes();

int sumCalories = menu.stream()
                      .mapToInt(Dish::getCalories) // IntStream으로 변환
                      .sum();

IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
Stream<Integer> boxed = intStream.boxed(); // boxed로 다시 Stream으로 복원 가능하다.
```
- sum()일 경우 기본값이 0으로 파악이 되지만 이러한 기본값을 예측할 수 없는 max() 같은거는 기본 값을 정할 수 없어 Optional의 형태인 OptionalInt를 제공한다.

```java
OptionalInt maxOp = menu.stream().mapToInt(Dish::getCalories).max();
int max = maxOp.orElse(0); // 값이 없다면 0을 사용
```
- Optional은 다양한 상황에 대처가 가능해진다.

#### 1) mapToObj
- 숫자형 스트림에서 map을 할 경우 굳이 boxed가 아닌 mapToObj를 이용할 수 있다.

```java
Stream<int[]> pair = IntStream.rangeClosed(1, 10)
                              .mapToObj(i -> new int[]{i, i + 1});
```

### 스트림 만들기
#### 1) nullable Stream
```java
String config = System.getProperty("config");

Stream<String> configStream = config == null ? Stream.empty() : Stream.of(config);

// 자바 9부터 nullableStream이 생김
Stream<String> configStream2 = Stream.ofNullable(config);

// nullable를 잘 활용하면 유용하게 사용가능해진다.
Stream<String> properies = Stream.of("config", "user", "key")
                                 .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
```

#### 2) 무한 스트림(iterate)
```java
Stream.iterate(0, n -> n + 2)
          .forEach(i -> {
              try {
                  TimeUnit.MICROSECONDS.sleep(10);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              System.out.println(i);
          });
```
- 무한 스트림을 만들 수 있다. 이러한 스트림을 언바운드 스트림이라고 한다.
- 이것이 컬렉션과 가장 큰 차이점이다. 컬렉션은 이러한 무한대의 값을 가질 수 없을 것이다.

#### 3) generate()
```java
Random random = new Random();
Stream.generate(() -> random.nextInt(30))
        .distinct()
        .limit(10)
        .forEach(i -> System.out.print(i + " "));
```
- generate는 supplier를 인수로받아 계속해서 이를 생성하는 기능을 가지고 있다.
- limit이 없다면 무한정 생성하게 될 것이다.
