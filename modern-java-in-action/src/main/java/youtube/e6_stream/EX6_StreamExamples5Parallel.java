package youtube.e6_stream;

/**
 * Created by Stranger on 2020/02/23
 */

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/** 병렬 프로그래밍에 대해 알아보았다.
 * 람다가 생긴 이유는 함수형 프로그래밍을 자바에서 사용하게 위함이기보다는 병렬 프로그래밍을 좀더 쉽게 제어할 수 있게 하기 위함이다.
 * - 멀티코어 프로세스에서 프로그래밍을 하면 race condition으로 인한 동기화 문제가 생기기 십상이다.
 * - 불변이 보장되고 모든 인풋에대해 아웃풋이 동일한 함수형 프로그래밍이 대두되는 것이다.
 *
 * >> 주의점 <<
 * - ORM 쓸때 지연로딩이라면 사용하면 문제가 생길 수 있다.
 * - 즉시로딩을하던지 데이터를 다 뽑은 후에 사용해야 한다.
 */

public class EX6_StreamExamples5Parallel {

    public static void main(String[] args) {
        final int[] sum = {0};
        int endInclusive = 1000000;
        long start1 = System.currentTimeMillis();
        IntStream.rangeClosed(0, endInclusive)
                .forEach(i -> sum[0] += i);
        System.out.println("                                      sum : " + sum[0] + ", time: " + (System.currentTimeMillis() - start1));

        final int[] sum2 = {0};
        long start2 = System.currentTimeMillis();
        IntStream.rangeClosed(0, endInclusive)
                .parallel()
                .forEach(i -> sum2[0] += i);
        System.out.println("          parallel sum (with side-effect) : " + sum2[0] + ", time: " + (System.currentTimeMillis() - start2));

        long start3 = System.currentTimeMillis();
        int streamSum = IntStream.rangeClosed(0, endInclusive)
                .sum();
        System.out.println("         stream sum (without side-effect) : " + streamSum + ", time: " + (System.currentTimeMillis() - start3));

        long start4 = System.currentTimeMillis();
        int streamSumParallel = IntStream.rangeClosed(0, endInclusive)
                .parallel()
                .sum();
        System.out.println("parallel stream sum (without side-effect) : " + streamSumParallel + ", time: " + (System.currentTimeMillis() - start4));
//
//        System.out.println("\n==============================================");
//        System.out.println("Stream");
//        long start5 = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .stream()
//                .map( i ->{
//                    try{
//                        TimeUnit.SECONDS.sleep(1);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    return i;
//                })
//                .forEach(System.out::println);
//        System.out.println("time : " + (System.currentTimeMillis() - start5) / 1000);

//        System.out.println("\n==============================================");
//        System.out.println("Parallel Stream (8 elements)");
//        long start6 = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .parallelStream()
//                .map( i ->{
//                    try{
//                        TimeUnit.SECONDS.sleep(1);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    return i;
//                })
//                .forEach(System.out::println);
//        System.out.println("time : " + (System.currentTimeMillis() - start6) / 1000);
//
//        System.out.println("\n==============================================");
//        System.out.println("Parallel Stream (9 elements)");
//        long start7 = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0)
//                .parallelStream()
//                .map( i ->{
//                    try{
//                        TimeUnit.SECONDS.sleep(1);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    return i;
//                })
//                .forEach(System.out::println);
//        System.out.println("time : " + (System.currentTimeMillis() - start7) / 1000);

        System.out.println("\n==============================================");
        System.out.println("Parallel Stream (8 elements)");
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "0");
        long start8 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
                .parallelStream()
                .map( i ->{
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    return i;
                })
                .forEach(System.out::println);
        System.out.println("time : " + (System.currentTimeMillis() - start8) / 1000);
    }
}
