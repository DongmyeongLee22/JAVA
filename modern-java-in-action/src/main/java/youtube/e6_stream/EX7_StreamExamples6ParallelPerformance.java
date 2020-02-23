package youtube.e6_stream;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Stranger on 2020/02/23
 */
public class EX7_StreamExamples6ParallelPerformance {
    
    private static void slowDown(){
        try {
            TimeUnit.MICROSECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
            slowDown();
        }
        return result;
    }

    public static long sequentialSum(long n) {
        // peek과 forEach의 효과는 같지만 peek은 중간 연사자이다.
        // 그리고 peek은 Consumer이므로 리턴값이 없다.
        return Stream.iterate(1L, i -> i + 1).limit(n).peek(i -> slowDown()).reduce((a, b) -> Long.sum(a, b)).get();
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).peek(i -> slowDown()).parallel().reduce(Long::sum).get();
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).peek(i -> slowDown()).reduce(Long::sum).getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).peek(i -> slowDown()).parallel().reduce(Long::sum).getAsLong();
    }

    public static void main(String[] args) {
        final long n = 10_000;

        /**
         * 10_000_000 숫자를 딱 4등분하는게 아닌 쪼개고 합치고를 계속 반복한다.
         * - 단순 덧셈과 같은 단순 계산방식은 쪼개고 합치고하는 시간이 더 올래 걸리기 때문에 스트림 병렬이 더 안좋을 수 있다.
         * - 허나 각 함수마다 10ms를 sleep하는 slowDown을 걸어주면 병렬이 더 빠른 것을 알 수 있다.
         */
        final long start1 = System.currentTimeMillis();
        System.out.println("     iterativeSum(n) : " + iterativeSum(n));
        System.out.println("                       " + (System.currentTimeMillis() - start1) + "ms\n");
        final long start2 = System.currentTimeMillis();
        System.out.println("    sequentialSum(n) : " + sequentialSum(n));
        System.out.println("                       " + (System.currentTimeMillis() - start2) + "ms\n");
        final long start3 = System.currentTimeMillis();
        System.out.println("      parallelSum(n) : " + parallelSum(n));
        System.out.println("                       " + (System.currentTimeMillis() - start3) + "ms\n");

        final long start4 = System.currentTimeMillis();
        System.out.println("        rangedSum(n) : " + rangedSum(n));
        System.out.println("                       " + (System.currentTimeMillis() - start4) + "ms\n");

        final long start5 = System.currentTimeMillis();
        System.out.println("parallelRangedSum(n) : " + parallelRangedSum(n));
        System.out.println("                       " + (System.currentTimeMillis() - start5) + "ms\n");
    }
}
