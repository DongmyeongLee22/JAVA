> [모던 자바 인 액션](http://www.yes24.com/Product/Goods/77125987?scode=029) 챕터 2. 동작 파라미터화 코드 전달하기를 읽어면서 정리한 내용입니다.

# 동작 파라미터화 코드 전달하기
- **동작 파라미터화를** 이용하면 자주 바뀌는 요구사항에 효과적으로 대응할 수 있습니다.
- 동작 파라미터화란 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미합니다.

### 1. 변화하는 요구사항에 대응하기
- 하나의 예제를 통해 코드를 점차 개선하면서 유연한 코드를 만드는 사례를 통해 알아보겠습니다.
- 어떤 농장 재고 목록에서 녹색 사과만 필터링하는 기능을 추가한다고 가정해보겠습니다.

<br>

- 우선 Color, Apple을 정의하였습니다.
```java
enum Color{
    GREEN, RED
}
class Apple {
    private Color color;
    private int weight;

    public int getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}
```

<br>

#### 1) 첫 번째 시도: 녹색 사과 필터링
- 녹색 사과를 필터링 하는 것은 이런식으로 할 수 있을 것입니다.

```java
public static List<Apple> filterGreenApples(List<Apple> inventory){
    List<Apple> result = new ArrayList<>();
    for(Apple apple :inventory){
        if (Color.GREEN.equals(apple.getColor())){
            result.add(apple);
        }
    }
    return result;
}
```
- 만약 빨간 사과도 필터링하고 싶어 졌을 땐 어떻게 해야할까요?

#### 2) 두 번째 시도: 색을 파라미터화
- filterGreenApples의 코드를 반복사용하지 않고 빨간 사과를 필터링할 수 있을까요?

```java
public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color){
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
        if (apple.getColor().equals(color)){
            result.add(apple);
        }
    }
    return result;
}
```
- color를 파라미터화하면 각 색깔별로 필터링을 진행할 수 있을 것입니다.
- 여기서 또 요구조건이 생겨 무게가 150 그램 이상인 사과만 필터링을 요구한다면 어떻게해야 할 까요?

```java
public static List<Apple> filterApplesByWeigh(List<Apple> inventory, int weight){
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
        if (apple.getWeight() > weight){
            result.add(apple);
        }
    }
    return result;
}
```
- 무게에 대응할 수 있게 메서드를 추가하면 됩니다.
- 하지만 이 메서드는 color별 필터링 하는 메서드의 코드랑 상당히 유사합니다.
- 이는 소프트웨어 공학의 DRY(don't repeat yourself)원칙을 어기는 것입니다.
- 그렇다고 한 메서드에 모든 파라미터를 넣고 메서드를 만든다면 코드는 매우 지저분해질 것이며 시간이 지나면 제대로된 의미를 파악하기 힘들게 될 것입니다.
- 이때 유용하게 사용할 수 있는 것이 **동작 파라미터화** 입니다.

### 2. 동작 파라미터화
- 동작 파라미터화를 수행하기 위해 참 또는 거짓만을 반환하는 Predicate를 정의해보겠습니다.

```java
interface ApplePredicate{
    boolean test (Apple apple);
}

class AppleHeavyWeightPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}

class AppleGreenColorPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple) {
        return Color.GREEN.equals(apple.getColor());
    }
}
```
- 전략 패턴을 활용하여 Predicate들을 런타임 시점에 동작하도록 하면 동작 파라미터화를 수행할 수 있게됩니다.
- filterApples 메서드 내부에서 컬렉션을 반복하는 로직과 컬렉션의 각 요소에 적용할 동작(Predicate)을 분리할 수 있다는 점에서 유연한 설계가 가능해 질 것입니다.

#### 1) 세 번째 시도: 추상적 조건으로 필터링

```java
public static List<Apple> filterApples(List<Apple> inventiry, ApplePredicate predicate){
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventiry) {
        if (predicate.test(apple)){
            result.add(apple);
        }
    }
    return result;
}
```
- 동작을 파라미터화하여 분리하였기 떄문에 이제 predicate만 따로 정의하고 런타임시 원하는 동작을 지정해주기만 하면 유연하게 해당 메서드를 사용할 수 있게 됩니다.
- 예를들어 빨간 사과이면서 100그램 이상인 사과들을 필터링한다고 하면 아래와 같이 Predicate를 정의하고 사용하기만 하면 됩니다.

```java
class AppleRedColorAndHeavyWeightPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() >= 100 && Color.RED.equals(apple.getColor());
    }
}

// 사용
List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedColorAndHeavyWeightPredicate());
```
- 모든 조건을 Predicate로 동작화하여 필터링할 수 있지만 메서드는 객체만 인수를 받으므로 test메서드를 ApplePredicate로 감싸서 전달해야하는 아쉬움이 있습니다.

### 3. 복잡한 과정 간소화
- 위의 예제는 유연하지만 매번 ApplePredicate를 구현하는 여러 구현체를 정의하고 인스턴스화 해야하는 번거로움이 있습니다.
- 자바에서는 클래스의 선언과 인스턴스화를 통시에 수행할 수 있도록 익명 클래스라는 기법을 제공합니다.

#### 1) 익명클래스
- 자바의 지역 클래스(블록 내부에 선언된 클래스)와 비슷한 개념입니다.
- 이름없응 클래스로 클래스 선언과 인스턴스화를 동시에할 수 있어 즉석에서 필요한 구현을 만들어 사용합니다.

#### 2) 네 번째 시도: 익명 클래스 사용
```java
List<Apple> heavyApples = filterApples(inventory
        , new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return apple.getWeight() > 100;
            }
        });
```
- 익명 클래스를 사용하여 선언과 동시에 인스턴스화하여 필터링을 진행할 수도 있습니다.
- 하지만 이 또한 코드가 장황합니다. 코드가 장황하면 구현 및 유지보수에 많은 시간이 소모되고 개발자 입장에서도 별로 달가워하지 않습니다.


#### 3) 다섯 번째 시도: 람다 표현식 사용
```java
List<Apple> heavyApples = filterApples(inventory, apple -> apple.getWeight() > 100);
```
- 자바8의 람다 표현식을 사용하면 훨씬 깔끔하게 구현이 가능합니다.

#### 4) 마지막 시도: 제네릭스를 활용하여 추상화
```java
public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for (T t : list) {
        if (p.test(t)){
            result.add(t);
        }
    }
    return result;
}
```
- 제네릭스를 활용하여 추상화를 하였기 때문에 Apple뿐만아니라 모든 종류의 데이터들도 필터링이 가능해집니다.
- 그렇기 때문에 아래와 같이 String, Integer도 원하는 Predicate를 정의하여 필터링을 할 수 있게됩니다.

```java
List<Apple> redApples = filter(inventory, apple -> Color.RED.equals(apple.getColor()));
List<Integer> greaterthanTen = filter(integerList, i -> i > 10);
List<String> longerthanTen = filter(stringList, s -> s.length() > 10);
```
