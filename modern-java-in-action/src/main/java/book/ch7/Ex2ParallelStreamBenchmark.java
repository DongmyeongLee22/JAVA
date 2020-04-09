package book.ch7;


import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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

  public static void main(String[] args) {
    // 테스트 전 샘플
    init();

    System.out.println("\n\n================================ forAndIterateAndLongStream ===========================");
    IntStream.rangeClosed(1, 1).forEach(i -> forAndIterateAndLongStream());

    System.out.println("\n\n================================ iterateAndForAndLongStream ===========================");
    IntStream.rangeClosed(1, 1).forEach(i -> iterateAndForAndLongStream());

    System.out.println("\n\n================================ longStreamAndIterateAndFor ===========================");
    IntStream.rangeClosed(1, 1).forEach(i -> longStreamAndIterateAndFor());


  }

  private static void longStreamAndIterateAndFor() {
    longstart();
    iterstart();
    forstart();
  }

  private static void iterateAndForAndLongStream() {
    iterstart();
    forstart();
    longstart();
  }

  private static void forAndIterateAndLongStream() {
    forstart();
    iterstart();
    longstart();
  }

  private static void longstart() {
    long start3 = System.currentTimeMillis();
    long seLong = sequentialSumUsingLongStream();
    long end3 = System.currentTimeMillis();
    System.out.println("Sequential LongStream Time : " + (end3 - start3) + "ms");

    long start4 = System.currentTimeMillis();
    long paLong = parallelSumUsingLongStream();
    long end4 = System.currentTimeMillis();
    System.out.println("Parallel LongStream Time   : " + (end4 - start4) + "ms");
  }

  private static void iterstart() {
    long start1 = System.currentTimeMillis();
    long seIter = sequentialSumUsingIterate();
    long end1 = System.currentTimeMillis();
    System.out.println("Sequential Iterate Time    : " + (end1 - start1) + "ms");

    long start2 = System.currentTimeMillis();
    long parIter = parallelSumUsingIterate();
    long end2 = System.currentTimeMillis();
    System.out.println("Parallel Iterate Time      : " + (end2 - start2) + "ms");
  }

  private static void forstart() {
    long start = System.currentTimeMillis();
    long forSum = forSum();
    long end = System.currentTimeMillis();
    System.out.println("forLoop Time               : " + (end - start) + "ms");
  }

  private static void init() {
    IntStream.rangeClosed(0, 5).forEach(i -> {
      sequentialSumUsingIterate();
      sequentialSumUsingLongStream();
      forSum();
      parallelSumUsingIterate();
      parallelSumUsingLongStream();
    });
  }

}
