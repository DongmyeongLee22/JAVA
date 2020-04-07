# Chapter 4. 스트림 소개
### 자바 스프림 특징
- 선언형: 더 간결하고 가독성이 좋아진다.
- 조립할 수 있다: 유연해짐
- 병렬화: 성능 좋아짐

### 스트림?
- 스트림이란 "데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소"라고 할 수 있다.

#### 1) 주요 특징
**파이프라이닝**
- 대부분의 스트림 연산은 스트림 연산끼리 연결해 파이프라인 구성이 가능하다.
- 그 덕분에 lazy, short-circuting등의 최적화를 얻을 수 있다.

**내부 반복**
- 스트림은 내부반복을 지원하기 때문에 간결한 코드 작성이 가능하다.

### 스트림과 컬렉션
- 데이터를 **언제** 계산하느냐가 컬렉션과 스트림의 가장 큰 차이다.
- 컬렉션은 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조이다.
- 그러므로 컬렉션을 추가, 삭제등 연산을 하기 위해서는 모든 요소가 메모리에 존재해야 한다.
- 반면 스트림은 이론적으로 요청할 때만 요소를 계산하는 고정된 자료구조이다.
- 스트림에서는 데이터를 추가, 삭제할 수 없다.
- 스트림은 요청하는 값만 추출(lazy하다)하기 때문에 사용자 입장에서는 결과적으로 producer와 consumer관계를 형성한다.

### 스트림 연산
```java
// 게속해서 사용할 데이터 값
@RequiredArgsConstructor
@Getter
@ToString(of = "name")
public class Dish {
    private final String name;
    private final boolean vegitarian;
    private final int calories;
    private final Type type;

    public enum Type{MEAT, FIST, OTHER}

    public static List<Dish> makeDishes(){
        return Arrays.asList(
                new Dish("port", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french", false, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FIST),
                new Dish("slamon", false, 450, Type.FIST)
        );
    }
}
```

#### 1) 중간 연산
- 중간 연산은 다른 스트림을 반환한다.
- 이러한 중간 연산들을 연결해서 파이프라이닝이 가능하다.
- 가장 중요한 특징은 최종 연산이 이루어지기 전까지 스트림은 아무 연산도 수행하지 않는다.

```java
public static void main(String[] args) {
     List<String> names = Dish.makeDishes()
             .stream()
             .filter(dish -> {
                 System.out.println("filtering: " + dish.getName());
                 return dish.getCalories() > 300;
             })
             .map(dish -> {
                 System.out.println("mapping: " + dish.getName());
                 return dish.getName();
             })
             .limit(2)
             .collect(Collectors.toList());
 }

filtering: port
mapping: port
filtering: beef
mapping: beef

```
- 출력을 확인해보면 딱 두번만 프린트된다.
- 즉 filter를 한번에 다 수행하는게 아니고 filter -> map -> limit 이런식으로 과정이 이루어진다.
- 이러한 이유는 limit 연산과 쇼트서킷이라고 불리는 기법 덕분이다.
- 또한 filter, map이 한 과정으로 통합되었는데 이를 루프 퓨전이라고 한다.

#### 2) 최종 연산
- 스트림 파이프라인에서 결과를 반환하는 연산이다.
-
