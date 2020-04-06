> [모던 자바 인 액션](http://www.yes24.com/Product/Goods/77125987?scode=029) 챕터 3. 람다표현식을 읽으면서 정리한 내용입니다.

# 람다 표현식
- 람다 표현식은 저번 게시글에서 본거와 같이 익명 클래스처럼 이름 없는 함수이면서 메서드를 인수로 전달할 수 있습니다.
- 람다 표현식을 어떻게 만들고, 사용하며 이를 통해 코드를 간결하게 만들 수 있는지에 대해 알아 보겠습니다.

### 1. 람다란?
- **람다 표현식은** 메서드로 전달할 수 있는 익명 함수를 하나의 식으로 표현할 수 있는 것입니다.
- 람다의 특징에 대해 알아보겠습니다.

**람다의 특징**
- 이름이 없으므로 **익명** 입니다.
- 람다는 특정 클래스에 종속되지 않으므로 메서드가 아닌 함수라고 부릅니다.(**함수**)
- 하지만 메서드처럼 파라미터 리스트, 본문, 변환 형식, 가능한 예외리스트를 포함합니다.
- 람다 표현식은 메서드 인수로 전달하거나 변수로 저장할 수 있습니다.(**전달**)
- 깔끔하게 코드를 작성할 수 있어 간결하게 코드 작성이 가능합니다.(**간결성**)

```java
Comparator<Integer> integerComparator = (Integer a, Integer b) -> b - a;
```
- Comparator를 람다식을 사용하여 구현하면 매우 간단하게 구현할 수 있습니다.
- ()부분을 **파라미터 리스트라고** 하며 해당 메서드의 파라미터가 들어갑니다.
- **화살표는** 파라미터 리스트와 바디를 구분합니다.
- 화살표 뒤쪽의 코드들을 **람다 바디라고하며** 람다의 반환 값에 해당하는 표현식 입니다.

```java
() -> {} // 아래와 동일
public void run(){}

() -> "Hello" // 아래와 동일
public String run(){return "Hello";}

() -> {return "Hello";} // 아래와 동일
public String run(){return "Hello";}
```
- 문법은 상당히 단순한 것을 알 수 있습니다.
- 해당 데이터를 바로 리턴한다면 따로 블럭을 생성하지 않아도 되는 것을 알 수 있습니다.

### 2. 람다는 어디에 사용해야할까?
- 람다는 함수형 인터페이스에서 사용할 수 있습니다.
- 함수형 인터페이스에 대해 알아보겠습니다.

#### 1) 함수형 인터페이스
- 함수형 인터페이스는 정확히 하나의 추상메서드를 저장하는 인터페이스 입니다.

```java
public interface Comparator<T>{
  int compare(T o1, T o2);
}

public interface Runnable{
  void run();
}
```
- Comparator, Runnable이 대표적인 함수형 인터페이스라고 할 수 있습니다.
- 함수형 인터페이스들은 오직 하나의 추상메서드만을 가지고 있기 때문에 해당 인터페이스를 구현하는 인스턴스를 람다식을 통해 구현할 수 있습니다.

### 3. 람다 활용: 실행 어라운드 패턴
- 자원을 처리할 때 보통 자원을 열고, 자원을 처리하고, 자원을 닫는 순서로 이루어집니다.
- 즉 실제 자원을 처리기 전 후를 감싸는 자원을 열고, 닫는 과정을 대부분 비슷합니다.
- 이를 실행 어라운드 패턴이라고 부릅니다.

```java
public String processFile() throws IOException{
    try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
        return br.readLine();
    }
}
```
- try-with-resources를 활용하여 파일의 값을 한줄 읽어오는 processFile 메서드가 있다고 가정해 보겠습니다.
- 만약 현재 메서드에서 한줄이 아닌 다른 기능을 사용하고 싶으면 어떻게 해야할까요?
- 저번 게시글에서 보았던 동작 파라미터화를 이용하면됩니다.

```java
@FunctionalInterface
interface BufferReaderProcessor{
    String process(BufferedReader br) throws IOException;
}
```
- 우선 함수형 인터페이스를 정의합니다.
- @FunctionalInterface는 자바에서 지원해주는 Annotation으로 해당 Annotation을 붙일 시 인터페이스에 두개 이상의 추상 메서드가 존재할 때 컴파일 에러로 잡아줍니다.

<br>

```java
public String processFile(BufferReaderProcessor p) throws IOException{
   try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
       return p.process(br);
   }
}
```
- processFile은 동작 파라미터화를 통해 BufferReaderProcessor의 구현에 따라 해당 결과를 달라지게 됩니다.

```java
public void temp() throws IOException {
    String oneLine = processFile(br -> br.readLine());
    String twoLines = processFile(br -> br.readLine() + br.readLine());
}
```
- 람다식을 활용하여 간단하게 한줄, 두줄 등 원하는 동작을 정의하여 사용할 수 있습니다.

### 4. 함수형 인터페이스 사용
- 자바 8에서는 기본적인 함수형 인터페이스를 제공해 줍니다.
- 대표적으로 Predicate, Consumer, Function 인터페이스등이 있습니다.

#### 1) Predicate
- Predicate는 간단히 말해 파라미터에 맞는 boolean값을 리턴해주는 기능을 제공합니다.
- 즉 파라미터가 존재하며 리턴값은 오직 boolean만 가능한 추상 메서드를 지원해주는 인터페이스 입니다.

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
```
- Predicate를 확인해보면 추상메서드는 test 메서드 하나만 존재합니다.

<br>

```java
public <T> boolean temp(T data, Predicate<T> p){
    return p.test(data);
}
boolean result = temp("Hello", s -> s.equals("Hello"));
```
- 정말 간단하게 Predicate를 사용해보았습니다.
- 해당 data를 정의된 Predicate로 검증하여 boolean을 리턴하는 것 입니다.
- Predicate는 추상메서드가 하나만 존재하는 함수형 인터페이스 이므로 람다식을 통해 정의할 수 있습니다.

#### 2) Consumer
- Consumer는 파라미터가 있으나 리턴값이 없는 추상 메서드를 가지고 있는 인터페이스 입니다.
- 그러므로 특정 파라미터에 맞는 어떤 동작을 수행하고 싶을 때 적합합니다.

```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}

public static <T> void forEach(List<T> list
                              , Consumer<T> c){
    for (T t : list) {
        c.accept(t);
    }
}
```
- forEach메서드에는 list와 Consumer가 존재합니다.
- list 요소들을 반복하며 Consumer 메서드에 요소들을 넘기면서 호출합니다.

```java
public static void main(String[] args) {
    List<Integer> integers = List.of(1, 2, 3, 4, 5);
    forEach(integers, i -> System.out.println(i));
}
```
- 람다식을 활용하여 해당 list들을 출력하도록 동작을 정의하여 수행할 수 있습니다.

#### 3) Function
- Function<T, R>은 파라미터가 존재하면서 리턴값이 존재하는 함수형 인터페이스 입니다.
- T값을 파라미터로 받아 R로 반환해주는 함수형 인터페이스 입니다.

```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}

public static <T, R> List<R> map(List<T> list, Function<T, R> f){
    List<R> ret = new ArrayList<>();
    for (T t : list) {
        ret.add(f.apply(t));
    }
    return ret;
}
```
- T값을 R로 변환해주기 때문에 map 메서등 적합합니다.

```java
public static void main(String[] args) {
    List<String> strings = List.of("hello", "dexter");
    List<String> map = map(strings, s -> s.toUpperCase()); // HELLO, DEXTER
}
```
- String List의 값들을 모두 대문자로 변환하여 map해주는 Function 함수형 인터페이스를 정의하였습니다.
- mapList에는 대문자 값들이 들어가 있게 될 것입니다.

#### 4) Supplier
- Supplier<T>는 파라미터가 존재하지 않지만 리턴값이 존제하는 함수형 인터페이스입니다.

```java
@FunctionalInterface
public interface Supplier<T> {

    T get();
}
```

- 파라미터가 존재하지 않는데 리턴값이 존재하는 이 Supplier는 어디에서 사용될까요?
- 간단한 예를 통해 Supplier의 사용처에 대해 알아보겠습니다.

```java
private static void printIfPositiveNumber(Integer integer, String string) {
    if (integer > 0) {
        System.out.println(string);
    }
}

private static String complexOperation() {
    try {
        // 복잡한 연산 후 String을 반환한다.
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return "Hello, World!";
}
```
- 파라미터의 integer가 양수일 경우에만 string을 출력하는 메서드가 있다고 가정해보겠습니다.
- 그리고 파라미터의 String은 complexOperation()가 반환하는 값을 입력 파라미터로 받습니다.
- complexOperation()은 1초간의 복잡한 연산 후 에 해당 Stirng을 반환한다고 가정해보겠습니다.

```java
public static void main(String[] args) {
    final List<Integer> numberList = Arrays.asList(-3, -2, -1, 0, 1, 2, 3);

    final long start = System.currentTimeMillis();
    for (Integer integer : numberList) {
        printIfPositiveNumber(integer, complexOperation());
    }
    System.out.println("동작 시간: " + (System.currentTimeMillis() - start) / 1000 + "sec");
}

// 출력
Hello, World!
Hello, World!
Hello, World!
동작 시간: 7sec
```
- numberList의 요소들을 forEach를 통해 위에서 정의한 메서드들을 실행시킵니다.
- 모든 연산이 끝나고 동작 시간은 몇초가 될까요?
- for문이 돌때마다 complexOperation()을 호출하므로 위의 출력과 같이 총 7초의 시간이 소모되는 것을 알 수 있습니다.

<br>

```java
private static void printIfPositiveNumber2(Integer integer, Supplier<String> supplier) {
    if (integer > 0) {
        System.out.println(supplier.get());
    }
}
```
- 새로운 메서드를 만들어 파라미터로 String을 받지않고 Supplier<String>을 받은 후 출력 시 supplier.get()을 통해 원하는 값을 리턴받아 출력하도록 해보겠습니다.

```java
public static void main(String[] args) {
        final List<Integer> numberList = Arrays.asList(-3, -2, -1, 0, 1, 2, 3);

        final long start = System.currentTimeMillis();
        for (Integer integer : numberList) {
            printIfPositiveNumber2(integer, () -> complexOperation());
        }
        System.out.println("동작 시간: " + (System.currentTimeMillis() - start) / 1000 + "sec");
}

// 출력
Hello, World!
Hello, World!
Hello, World!
동작 시간: 3sec
```
- 그리고 위에서 했던 테스트를 똑같이 진행하지만 해당 메서드를 호출할 때 complexOperation()을 리턴하는 supplier 구현하여 넘겨주었습니다.
- 출력을 보면 7초였던 동작 시간이 3초로 줄어든 것을 알 수 있습니다.
- 그 이유는 람다식을 활용하여 complexOperation()을 supplier를 통해 넘겨주었기 때문입니다.
- 그러므로 if(integer > 0) 조건이 만족하여 supplier.get() 메서드가 호출될 때 complexOperation()이 호출되기 때문에 동작 시간이 줄어들게 된 것입니다.


#### 기본타입 특화

```java
public static void main(String[] args) {
    IntPredicate p1 = (int i) -> i % 2 == 0;
    // 박싱이 없어 메모리 효율적

    Predicate<Integer> p2 = (Integer i) -> i % == 0;
    // 박싱하기 때문에 힙 영역어 저장되어 메모리 비효율적
}
```
- 기본타입이 특화된 타입을 제공한다.

### 3.5 형식 검사, 형식 추론, 제약
#### 형식 검사
- 람다식으로 결정될 타입을 **Target Type** 이라고 한다.

```java
filetr(i, a -> a.getWeight() > 10);
// 이경우 filter의 두번째 메서드의 타입이 타겟 타입일 것이다.
```

#### void는 독특한 호환을 가진다.
```java
Predicate<Integer> p =  i -> list.add(i);
Consumer<Integer> p2 = i -> list.add(i);
```
- Consumer는 반환값이 void이지만 list.add는 boolean을 반환한다.
- 하지만 이를 유효한 코드로 인식한다.


#### 형식 추론
- 함수 디스크립터를 통해 알 수 있는 정보가 충분하면 형식을 생략해도 된다.
- 함수 디스크립터는 람다 표현식의 시그니처를 서술하는 메서드이다.

#### 지역 변수 사용
- 람다는 외부 변수를 사용할 수 있다.
- 하지만 그 변수들은 final 기능을 가져야 한다.

**이유**
- 인스턴스 변수와 지역 변수는 태생부터 다르다.
- 인스턴스 변수는 힙에 저장되지만 지역 변수는 스택에 위치한다.
- 만약 지역 변수가 변경될 수 있는 값인데 람다식에 넘어간다면 해당 값이 또 다른 람다식으로 전파될지 알 수 없다.
- 여러 이유가 있겠지만 병렬화를 사용하기 위해선 해당 값이 변경되지 않음을 확신할 수 있어야 한다.

### 3.6 메서드 참조
- 기존 메서드를 람다 처럼 전달하여 일급 함수의 특징을 지니게 할 수 있다.

```java
public void test() {
    // 1
    List<String> str = Arrays.asList("a", "b", "A");
    str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
    str.sort(String::compareToIgnoreCase);

    // 2
    Function<String, Integer> f = (s) -> Integer.parseInt(s);
    Function<String, Integer> f2 = Integer::parseInt;

    // 3
    Function<String, Boolean> isContainA = s -> this.containA(s);
    Function<String, Boolean> isContainA2 = this::containA;
    }

    private boolean containA(String s){
    return s.contains("A");
    }
}
```
- 첫번째 파라미터가 호출하는 객체고, 두번째 파라미터가 호출하는 메서드의 파라미터일 경우 해당 클래스로 메서드 참조가 가능하다.
- 2, 3과 같이 타입이 호출하면 타입으로, 객체가 호출하면 객체로 사용 가능하다.

#### 생성자 참조
```java
public static void main(String[] args) {
    // 기본 생성자
    Supplier<Temp> t = () -> new Temp();
    Supplier<Temp> t2 = Temp::new;

    // 파라미터가 있는 생성자
    Function<String, Temp> t3 = s -> new Temp(s);
    Function<String, Temp> t4 = Temp::new;
}

static class Temp{
    private String name;

    public Temp() {
    }

    public Temp(String name) {
        this.name = name;
    }
}
```
- 기본 생성자, 파라미터가 있는 생성자 모두 생성자 참조가 가능하다.

### 3.8 람다 표현식을 조합할 수 잇는 유용한 메서드들
#### Comparator 활용
```java
public static void main(String[] args) {
    List<Apple> apples = Arrays.asList(
            new Apple(3, "A"),
            new Apple(7, "B"),
            new Apple(1, "A"),
            new Apple(2, "B")
    );

    // comparing으로 정렬을 간단하게 정의할 수 있다.
    Comparator<Apple> comparator = Comparator.comparing(Apple::getWeight);
    apples.sort(comparator);
    System.out.println(apples);

    // reversed를 통해 정렬을 뒤집을 수 있다.
    apples.sort(comparator.reversed());
    System.out.println(apples);

    // 같을 경우 추가 정렬 조건 적용 가능
    apples.sort(comparator.thenComparing(Apple::getType));
    System.out.println(apples);
}

@Getter
@ToString
@AllArgsConstructor
static class Apple{
    int weight;
    String type;
}
```

#### Predicate 활용
```java
public static void main(String[] args) {
    Predicate<Apple> isAType = a -> "A".equals(a.getType());

    // negate로 조건의 반대를 적용할 수 있음
    Predicate<Apple> isNotAType = isAType.negate();

    // (isATupe && weight > 10) || (weight < 3)
    Predicate<Apple> complexPredicate = isAType.and(a -> a.getWeight() > 10)
                                               .or(a -> a.getWeight() < 3);
}
```
- negate(), and(), or()등 유용한 메서드들이 존재한다.

#### Function 활용
```java
public static void main(String[] args) {
    Function<Integer, Integer> plus2 = i -> i + 2;
    Function<Integer, Integer> mul3 = i -> i * 3;

    // 호출하는 plus2부터 적용됨. plus2 -> mul3
    Function<Integer, Integer> plus2AndMul3 = plus2.andThen(mul3);
    System.out.println(plus2AndMul3.apply(3));
    // ( 3 + 2 ) * 3 = 15;

    // 메서드 전달 인자부터 적용됨. mul3 -> plus 2
    Function<Integer, Integer> mul3AndPlus2 = plus2.compose(mul3);
    System.out.println(mul3AndPlus2.apply(3));
    // ( 3 * 3 ) + 2 = 11;
}
```
- andthen, compose와 같이 함수를 연결시킬 수 있는 메서드들이 존재한다.
