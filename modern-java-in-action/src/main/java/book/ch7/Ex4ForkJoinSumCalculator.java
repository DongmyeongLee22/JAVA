package book.ch7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class Ex4ForkJoinSumCalculator extends RecursiveTask<Long> {

  private long[] numbers; // 연산할 값이 들어있는 배열
  private int start;
  private int end;
  private static final long THRESHOLD = 10_000; // THRESHOLD 이라하면 분리하지 않는다.

  public Ex4ForkJoinSumCalculator(long[] numbers){
    this(numbers, 0, numbers.length);
  }

  private Ex4ForkJoinSumCalculator(long[] numbers, int start, int end){
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

    Ex4ForkJoinSumCalculator leftTask = new Ex4ForkJoinSumCalculator(numbers, start, start + length / 2);
    leftTask.fork(); // left는 비동기로 실행한다.

    Ex4ForkJoinSumCalculator rightTask = new Ex4ForkJoinSumCalculator(numbers, start + length / 2, end);
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
    ForkJoinTask<Long> task = new Ex4ForkJoinSumCalculator(numbers);
    Long result = new ForkJoinPool().invoke(task); // ForkJoinPool은 보통 애플리케이션에서 싱글톤으로 사용해야한다.

    System.out.println("result = " + result);
  }
}
