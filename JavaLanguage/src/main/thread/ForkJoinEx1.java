package main.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/* fork와 join
    fork: 해당 작업을 쓰레드 풀의 작업 큐에 넣는다. 비동기 메서드
    join: 해당 작업의 수행이 끝날 때까지 기다렸다가, 수행이 끝나면 그결과를 반환한다. 동기메서드

    이 결과보다 for문이 더 빠르다.왜냐면 작업을 나누고 다시 합치는데 걸리는 시간이 있기 때문에../
 */
public class ForkJoinEx1 {
    static final ForkJoinPool pool = new ForkJoinPool();

    public static void main(String[] args) {
        long from = 1L, to = 100_100_100L;

        SumTask task = new SumTask(from, to);

        long start = System.currentTimeMillis();
        Long result = pool.invoke(task);

        System.out.println("Elapsed time(4 Core):" + (System.currentTimeMillis() - start));

        System.out.printf("sum of %d~%d=%d%n", from, to, result);
        System.out.println();

        result = 0L;
        start = System.currentTimeMillis();
        for (long i = from; i < to; i++) {
            result += i;

            System.out.println("Elapsed time(1 Core):" + (System.currentTimeMillis() - start));

            System.out.printf("sum of %d~%d=%d%n", from, to, result);
        }
    }
}

class SumTask extends RecursiveTask<Long> {

    long from, to;

    public SumTask(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    protected Long compute() {
        long size = to - from + 1;

        if (size <= 5) {
            return sum();
        }

        long half = (from + to) / 2;

        SumTask leftSum = new SumTask(from, half);
        SumTask rightSum = new SumTask(half, to);

        leftSum.fork();

        return rightSum.compute() + leftSum.join();
    }

    long sum() {
        long tmp = 0L;

        for (long i = from; i < to; i++) {
            tmp += i;
        }

        return tmp;
    }
}
