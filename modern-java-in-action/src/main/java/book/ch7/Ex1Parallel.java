package book.ch7;

import java.util.stream.Stream;

public class Ex1Parallel {
  public long sequentialSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
      .limit(n)
      .parallel()
      .reduce(0L, Long::sum);
  }

  public void test() {
    Stream.generate(Math::random)
      .parallel() // 병렬
      .filter(d -> d < 30.0)
      .sequential() // 순차적
      .filter(d -> d > 10.0)
      .parallel(); // 병렬 (마지막 호출로 수행된다.)
  }

  public static void main(String[] args) {
System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
  }
}
