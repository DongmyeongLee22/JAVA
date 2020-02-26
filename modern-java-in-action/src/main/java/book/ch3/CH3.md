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
