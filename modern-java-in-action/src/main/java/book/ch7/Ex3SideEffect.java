package book.ch7;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Ex3SideEffect {

  public static long sequentialSum(long n){
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).forEach(accumulator::add);
    return accumulator.total;
  }

  public static long parallelSum(long n){
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
    return accumulator.total;
  }

  static class Accumulator{
    public long total = 0;
    public void add(long value) { total += value;}
  }

  public static void main(String[] args) {
    long N = 10_000_000L;

    System.out.println("===================== Sequential Sum ==========================");
    IntStream.rangeClosed(1, 10).forEach(i -> System.out.println("sequntial: " + sequentialSum(N)));


    System.out.println("\n\n===================== Parallel Sum ==========================");
    IntStream.rangeClosed(1, 10).forEach(i -> System.out.println("parallel : " + parallelSum(N)));
  }
}
