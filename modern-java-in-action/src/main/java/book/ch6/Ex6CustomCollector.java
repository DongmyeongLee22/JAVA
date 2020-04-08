package book.ch6;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class Ex6CustomCollector {

  // 비효율
  public static boolean isPrime(List<Integer> primes, int candidate){
    return primes.stream().noneMatch(i -> candidate % i == 0);
  }

  // root 값까지만 확인
  public static boolean isPrime2(List<Integer> primes, int candidate){
    int root = (int) Math.sqrt(candidate);
    return primes.stream()
                 .takeWhile(i -> i <= root)
                 .noneMatch(i -> candidate % i == 0);
  }

  // 자바 8에서 takeWhile 구현하여 사용
  public static boolean isPrime3(List<Integer> primes, int candidate){
    int root = (int) Math.sqrt(candidate);
    return takeWhile(primes, i -> i <= root).stream()
                                            .noneMatch(i -> candidate % i  == 0);
  }

  public static <T> List<T> takeWhile(List<T> list, Predicate<T> p){
    int i = 0;
    for(T item : list){
      if (!p.test(item)){
        return list.subList(0, i);
      }
      i++;
    }
    return list;
  }

  static class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>>{

    // 초기값을 설정한다.
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
      return () -> new HashMap<Boolean, List<Integer>>() {
        {
          put(true, new ArrayList<Integer>());
          put(false, new ArrayList<Integer>());
        }
      };
    }

    // isPrime3을 통해 true, false를 구하고 그에 맞는 map에 값을 넣는다.
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
      return (acc, candidate) ->
        acc.get(isPrime3(acc.get(true), candidate)).add(candidate);
    }

    // 병렬처리 시에는 map1에 map2를 추가해주면된다.
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
      return (map1, map2) -> {
        map1.get(true).addAll(map2.get(true));
        map1.get(false).addAll(map2.get(false));
        return map1;
      };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
      return Function.identity();
    }

    // 소수를 구하는 Collector는 다중쓰레드에서 동시에 accumulator를 사용할 수 없고 순서에 영향을 받으니 IDENTITY만 가진다.
    @Override
    public Set<Characteristics> characteristics() {
      return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
  }

  static public Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n){
    return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
  }

  static public Map<Boolean, List<Integer>> partitionPrimes(int n) {
    return IntStream.rangeClosed(2, n)
      .boxed()
      .collect(
        () -> new HashMap<Boolean, List<Integer>>() {
          {
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
          }
        },
        (acc, candidate) -> acc.get(isPrime3(acc.get(true), candidate)).add(candidate),
        (map1, map2) -> {
          map1.get(true).addAll(map2.get(true));
          map1.get(false).addAll(map2.get(false));
        }
      );
  }

  public static void main(String[] args) {
    Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(30);
    Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector = partitionPrimesWithCustomCollector(30);
    Map<Boolean, List<Integer>> booleanListMap = Ex4Grouping.partitionPrimes(30);

    System.out.println(partitionPrimes);
    System.out.println(partitionPrimesWithCustomCollector);
    System.out.println(booleanListMap);
  }
}

