package main.Lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
    Collection나 Iterator와 같은 인터페이스를 이용해 컬렉션을 다루는 방식을 표준화 했지만
    List는 Collections.sort, 배열은 Arrays.sort를 사용하듯이 중복이 있다.
    이를 해결하기 위해 나온것이 Stream이다. 스트림은 데이터 소스를 추상화 하고 데이터를 다루는데 자주 사용되는 메서드들을 정의해놓았다.
    스트림을 사용하면 배열이나 컬렉션 뿐만 아니라 파일에 저장된 데이터도 모두 같은 방식으로 다룰 수 있다.
 */
public class StreamEx {

    public static void main(String[] args) {

        String[] strArr = {"aa", "bb", "cc"};
        List<String> strList = Arrays.asList(strArr);

        //위의 컬렉션들을 이렇게 스트림으로 만들 수 있다.
        Stream<String> strStream1 = Arrays.stream(strArr);
        Stream<String> strStream2 = strList.stream();

        // 데이터 소스의 데이터들을 읽어서 정렬하고 화면에 출력하기
        // 데이터 소스가 정렬되는게 아니다!!
        strStream1.sorted().forEach(System.out::println);
        strStream2.sorted().forEach(System.out::println);


        // 옛날에는 이렇게 했어야 한다.
        Arrays.sort(strArr);
        Collections.sort(strList);

        for (String s : strList) {
            System.out.println(s);
        }

        for (String s : strArr) {
            System.out.println(s);
        }

        // 스트림은 데이터 소스를 변경하지 않는다. 데이터를 읽기만 할뿐 정렬해도 변경되지 않는다.
        //List<String> sortedList = strStream2.sorted().collect(Collectors.toList());

        // 스트림은 Iteraotr와 같이 일회용이다.
        strStream1.sorted().forEach(System.out::println);
        //long count = strStream1.count();
        // 이러면 에러난다. 왜냐하면 이미 스트림이 닫혔기 때문이다. 실행해보면 45라인에서 에러난다. 왜냐하면 위에서 이미 썻기때문에!!

        // 스트림은 작업을 내부 반복으로 처리한다.
        // 내부 반복은 메서드의 내부에 숨길 수 있다는 것을 의미한다.
        strStream1.forEach(System.out::println);
        strStream1.forEach((s) -> System.out.println(s));// 위랑 동일하다

        /*스트림의 연산
        // 중간 연산과 최종 연산이 있다.
        // 중간연산은 연산결과가 스트림인 연산이므로 스트림에 연속해서 사용가능하다
        // 최종연산은 결과가 스트림이 아니라서 한번만 사용가능하다
         forEach이전은 다 중간연산이고 그 이후가 최종 연산이 된다.*/
        strStream1.distinct().limit(5).sorted().forEach(System.out::println);

        /* 중간연산은 최종 연산이 실행되기 전까지는 지정만하는것이지 실제 실행안됨
         즉 최종연산이 필요하다*/

        // Stram<T> 이지만 기본 타입들은 IntStream, LongStream 등 간단하게 사용 가능

        /* 병렬 스트림
           - 이렇게 병렬 수행을 parallel 으로 간단하게 구현가능하다.
           - sequential은 병럴로 처리되지 않게하는 것이다
           - 기본이 seq이므로 굳이 안적어도 되나 parallel을 취소할 때 사용
         */
        int sum = strStream1.parallel()
                .mapToInt(s -> s.length())
                .sum();

        // ========================================== 스트림 만들기 ==========================================


        // 컬렉션
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.stream();
        stream.forEach(System.out::println);

        // 배열
        Stream<String> arrStream1 = Stream.of("a", "b", "c");
        Stream<String> arrStream2 = Arrays.stream(new String[]{"a", "b", "c"}, 0, 3);

        // 범위 지정
        IntStream range = IntStream.range(1, 5); // 1, 2, 3, 4
        IntStream range2 = IntStream.rangeClosed(1, 5); // 1, 2, 3, 4, 5

        // 무한 스트림, 무한이므로 제한 해줘야 함
        IntStream ints = new Random().ints();
        ints.limit(5).forEach(System.out::println);

        // 범위 지정가능, 마지막 포함 X
        IntStream ints1 = new Random().ints(0, 10);

        /*iterate(), generate()*/
        // seed 값부터 계속 반복한다.
        Stream<Integer> evenStream = Stream.iterate(0, n -> n + 2);

        // generate는 매개변수를 가질 수 없다.
        Stream<Double> randomStream = Stream.generate(Math::random);

        // iterate, generate는 이런 기본형 스트림 타입을 사용 할 수 없다.
        //IntStream evenStream = Stream.iterate(0, n -> n + 2);
        //DoubleStream randomStream = Stream.generate(Math::random);

        // 이렇게 서로서로 변환이 가능하다.
        Stream<Integer> iterate = Stream.iterate(0, n -> n + 2);
        IntStream intStream = iterate.mapToInt(Integer::valueOf);
        Stream<Integer> boxed = intStream.boxed();

        // 빈 스트림, 스트림 연산 결과가 없을땐 null 보다 빈 스트림이 좋다.
        Stream<Object> empty = Stream.empty();

        // 스트림 연결
        String[] str1 = {"123", "123"};
        Stream<String> str11 = Stream.of(str1);
        Stream<String> str12 = Stream.of(str1);
        Stream<String> concat = Stream.concat(str11, str12);
    }

}
