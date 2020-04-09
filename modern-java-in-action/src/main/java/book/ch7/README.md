# Chapter 7. 병렬 데이터 처리와 성능

### 병렬 스트림

#### 1) 병렬 스트림 사용

```java
public long sequentialSum(long n){
    return Stream.iterate(1L, i -> i + 1)
                 .limit(n)
                 .parallel()
                 .reduce(0L, Long::sum);
}
```
- parallel만 붙여 준다면 정해진 청크로 분할하여 병렬로 연산을 수행한다.

```java
public void test(){
  Stream.generate(Math::random)
        .parallel() // 병렬
        .filter(d -> d < 30.0)
        .sequential() // 순차적
        .filter(d -> d > 10.0)
        .parallel(); // 병렬 (마지막 호출로 수행된다.)
}
```
- sequential을 호출하면 순차적으로 수행할 수 있게 된다.
- 가장 마지막에 호출된 것이 전체 파이프 라인에 영향을 미치므로 병렬로 수행될 것이다.

```java
System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
```
- 병렬은 내부적으로 ForkJoinPool을 이용하여 수행된다.
- 따로 설정하지 않는한 컴퓨터의 프로세서 수와 동일한 수로 병렬 처리를 진행한다.
- 혹은 위와같이 직집 프로퍼티를 설정할 수 있다.
- 단 각 스트림 개별적으로는 설정이 불가능하다.


#### 2) 성능확인
- 10_000_000까지 수를 더하는 함수를 통해 성능을 확인해보자.

```java
public class Ex2ParallelStreamBenchmark {
  private static final long N = 10_000_000L;

  public static  long forSum(){
    long ret = 0;
    for(long i = 1L ; i <= N; i++){
      ret += i;
    }
    return ret;
  }

  // sequential iterate 사용
  public static long sequentialSumUsingIterate(){
    return Stream.iterate(1L, i -> i + 1L).limit(N).reduce(0L, Long::sum);
  }

  // parallel iterate 사용
  public static  long parallelSumUsingIterate(){
    return Stream.iterate(1L, i -> i + 1L).parallel().limit(N).reduce(0L, Long::sum);
  }

  // sequential LongStream 사용
  public static  long sequentialSumUsingLongStream(){
    return LongStream.rangeClosed(1, N).reduce(0L, Long::sum);
  }

  // parallel LongStream 사용
  public static  long parallelSumUsingLongStream(){
    return LongStream.rangeClosed(1, N).parallel().reduce(0L, Long::sum);
  }
}
```
- 해당 테스트의 결과는 아래와 같다.

```html
================================ forAndIterateAndLongStream ===========================
forLoop Time               : 7ms
Sequential Iterate Time    : 197ms
Parallel Iterate Time      : 274ms
Sequential LongStream Time : 7ms
Parallel LongStream Time   : 2ms


================================ iterateAndForAndLongStream ===========================
Sequential Iterate Time    : 407ms
Parallel Iterate Time      : 718ms
forLoop Time               : 8ms
Sequential LongStream Time : 8ms
Parallel LongStream Time   : 3ms


================================ longStreamAndIterateAndFor ===========================
Sequential LongStream Time : 10ms
Parallel LongStream Time   : 3ms
Sequential Iterate Time    : 177ms
Parallel Iterate Time      : 389ms
forLoop Time               : 6ms
```
- 정확하진 않겠지만 해당 결과를 보면 추측이 가능하다.
- LongStream을 이용한 병렬이 가장 성능이 좋고 직렬 LongStream과 forLoop는 비슷하다.
- **Iterate일 경우는 매우 느릴뿐만 아니라 신기하게도 병렬이 더 성능이 나쁜것을 알 수 있다.**
  - 이는 iterate의 성질을 이해하고 있어야 한다.
  - iterate연산은 이전값을 이용해 계산하기 때문에 순차적으로 연산을 하기때문에 분할하여 병렬로 수행하기 어렵다.
  - 그러므로 병렬 수행하더라도 결국은 순차적으로 계산하기 때문에 쓰레드를 만드는 오버헤드만 증가시킨다.
  - 그리고 LongStream과 다르게 따로 언박싱 가정이 필요하므로 iterate가 가장 느리게 된다.
- 이렇듯 병렬 수행은 해당 작업의 특성을 잘 이해하고 수행해야지, 오히려 성능에 악 영향을 끼칠 수 있다.


#### 3) 병렬 스트림 효과적으로 사용하기
**1. 확신이 서지 않으면 직접 측정하라**
**2. 박싱을 주의하라**
- 기본형으로 제공해주는 IntStream, LongStream을 사용할 수 있으면 이를 사용하면 된다.

**3. 특정 연산은 성능이 더 안좋다**
- limit, findFirst와 같이 순서에 의존하는 연산은 성능이 더 안좋다.
- 그러므로 이를 주의하자.(findAny는 순서와 상관없으니 괜찮음)

**4. 소량의 데이터는 병렬화를 만드는게 더 비용이 든다**

**5. 자료구조를 잘 파악하라**
- ArrayList는 분할이 간단하지만 LinkedList는 분할시 모든 요소를 탐색해야할 것이다.
- rangeOf 메서드들도 범위를 지정하기때문에 분할이 간단하다.

**6. 중간 연산의 특성을 잘 파악하자**
- filter와 같이 스트림의 길이를 예측할 수 없는 기능들은 병렬을 효율적으로 처리하지 못할 수 도 있다.

**7. 최종 병합 과정의 비용을 잘 파악하라**
- Collector의 combinder와 같이 병렬을 병합하는 과정이 비용이 크다면 병렬 병합할 때의 비용으로 인해 성능 향상을 이루지 못할 수 있다.


### 포크/조인 프레임워크
- 자바의 ForkJoin을 이용하여 병렬 처리를 직접 구현할 수 있다.
- 배열의 모든 숫자를 더하는 기능을 구현해보자.

```java
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

  private long[] numbers; // 연산할 값이 들어있는 배열
  private int start;
  private int end;
  private static final long THRESHOLD = 10_000; // THRESHOLD 이라하면 분리하지 않는다.

  public ForkJoinSumCalculator(long[] numbers){
    this(numbers, 0, numbers.length);
  }

  private ForkJoinSumCalculator(long[] numbers, int start, int end){
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    // 길이
    int length = end - start;
    if (length <= THRESHOLD){
      return computeSequentially();
    }

    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
    leftTask.fork(); // left는 비동기로 실행한다.

    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
    Long rightResult = rightTask.compute(); // rightTask는 동기로 수행한다. (한쪽은 동기로 하면 쓰레드를 조금 더 효율적으로 사용한다.)
    Long leftResult = leftTask.join(); // leftTask의 결과를 가져온다.(결과가 없으면 기다림)
    return leftResult + rightResult;
  }

  private long computeSequentially() {
    long sum = 0;
    for(int i = start; i < end; i++){
      sum += numbers[i];
    }
    return sum;
  }

  // ============================================== 메인 메서드 ==============================================

  public static void main(String[] args) {
    long n = 10_000_000;
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    Long result = new ForkJoinPool().invoke(task); // ForkJoinPool은 보통 애플리케이션에서 싱글톤으로 사용해야한다.

    System.out.println("result = " + result);
  }
}
```
- 사실 이 결과는 배열을 사용하기 때문에 이전에 구현한 방법보다 훨씬 느릴 것이다.
- 위의 병렬은 간단한 작업을 수행하기 때문에 쓰레드별 작업시간이 거의 동일할 것이다.
- 하지만 복잡한 연산이 있다면 개별 작업시간이 다를 것이고 이는 제대로된 병렬 수행이 되지 않을 수 있다.
- ForkJoin는 이를 작업 훔치기 기법을 통해 해결한다.
- 각 스레드는 자신에게 할당된 태스크를 포함하는 이중 연결 리스트를 참조하고, 작업이 끝날 때 큐의 헤드에서 태스크를 가져와 처리한다.
- 이때 다른 스레드는 자신의 작업을 완료했다면 다른 스레드의 큐의 테일에서 작업을 훔쳐오게 된다.

### Spliterator 인터페이스
- 자바 8에서는 Spliterator 인터페이스를 제공하며 병렬작업에 특화되어있다.
- 탐색하려는 데이터를 포함하는 스트림을 어떻게 병렬화할 것이니 정의할 수 있다.

```java
public int countWordsInteratively(String s) {
  int counter = 0;
  boolean isSpace = false;
  for (char c : s.toCharArray()) {
    if (Character.isWhitespace(c)) {
      isSpace = true;
    } else {
      if (isSpace) counter++;
      isSpace = false;
    }
  }
  return counter;
}
```
- Word를 셀 수 있는 메서드이다.

```java
String str = "Hello    World, My Name        is        Jayden. I`m  very   sleep.";
```
- 해당 string에 대한 값을 당연히 9를 반환할 것이다.

```java
@Getter
@RequiredArgsConstructor
static class WordCounter {
  private final int counter;
  private final boolean lastSpace;

  public WordCounter accumulate(Character c) {
    if (Character.isWhitespace(c)) {
      return lastSpace ? this : new WordCounter(counter, true);
    } else {
      return lastSpace ? new WordCounter(counter + 1, false) : this;
    }
  }

  public WordCounter combine(WordCounter wordCounter) {
    return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
  }
}
```
- 스트림에서 사용하기 위해선 위와같이 만들어 reduce에 사용할 수 있다.

```java
public int countWord(Stream<Character> stream) {
  return stream.reduce
    (new WordCounter(0, true),
      WordCounter::accumulate,
      WordCounter::combine).getCounter();
}
```
- 이제 stream으로 word 수를 구할 수 있다.

```java
public void test(){
  int space2 = main.countWord(toStream(str));
  System.out.println("space2 = " + space2);

  // 병렬로 수행하면 정답이 이상하다. 왜냐하면 공백이 여러개 있을 때 문자열을 임의로 나누므로 계산이 이상해질 수 있다.
  int space3 = main.countWord(toStream(str).parallel());
  System.out.println("space3 = " + space3);
}
private static Stream<Character> toStream(String str) {
  return IntStream.range(0, str.length()).mapToObj(str::charAt);
}
```
- sequential의 경우 정확히 9를 반환하지만 병렬 스트림은 9 이상의 랜덤한 숫자를 반환하게 된다.
- 이를 해결하기 위해 Spliterator를 구현해보자.

```java
@RequiredArgsConstructor
public class Ex5WordCounterSpliterator implements Spliterator<Character> {

  private final String string;
  private int currentChar = 0;


  // consumter에게 현재 사용할 요소를 넘겨줘 작업을 수행할 수 있게 해준다.
  @Override
  public boolean tryAdvance(Consumer<? super Character> action) {
    action.accept(string.charAt(currentChar++)); // 현재 문자를 소비
    return currentChar < string.length(); // 소비할 문자가 남아있는가?
  }

  // 해당 작업을 분할하는 메서드
  @Override
  public Spliterator<Character> trySplit() {
    int currentSize = string.length() - currentChar;
    if (currentSize < 10){
      return null; // 더이상 나누지 않는다.
    }
    for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++){
      if (Character.isWhitespace(string.charAt(splitPos))){

        // 처음부터 splitPos까지 문자열을 파싱할 새로운 Spliterator를 정의한다.
        Ex5WordCounterSpliterator spliterator = new Ex5WordCounterSpliterator(string.substring(currentChar, splitPos));
        currentChar = splitPos;
        return spliterator;
      }
    }
    return null;
  }

  @Override
  public long estimateSize() {
    return string.length() - currentChar;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    // 문자열 순서가 유지(ORDERED), 사이즈가 정확(SIZED), 분할된 작업도 사이즈가 정확(SUBSIZED), 불변(IMMUTABLE)
  }
}
```
- 이제 이 Spliterator를 통해 병렬 스트림을 만들 수 있다.

```java
Spliterator<Character> spliterator = new Ex5WordCounterSpliterator(str);
Stream<Character> parallelStream = StreamSupport.stream(spliterator, true); // true일 경우 병렬 스트림이다.
int space4 = main.countWord(parallelStream);
```
- 해당 결과는 병렬로 수행하면서 정확한 결과를 반환해준다.
