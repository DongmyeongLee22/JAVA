package youtube.e9_method_reference;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

/**
 * Created by Stranger on 2020/02/23
 */

/**
 * Method Reference도 First class citizen으로 사용할 수 있는것을 알아본다.
 * 1. function에 method를 넘겨준다.
 * 2. function의 return값이 method이다.
 * 3. datastructure에 method를 저장한다.
 */
public class EX1_MethodReferenceExamples {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);
        // println도 Consumer와 같이 어떤 타입이든지 받을 수 있고 리턴은 void이다.
        // 그러므로 ::prinln을 통해 println을 넘겨주는 것도 상관이 없게된다.


        Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                .stream()
                // .sorted() // BigDecimal은 Comparable Type이라 sorted가 된다. 혹은 Comparator를 만들어 사용하면 된다.
                // .sorted((bd1, bd2) -> bd1.compareTo(bd2))
                // .sorted(BigDecimalUtil::compare) // static method 사용
                .sorted(BigDecimal::compareTo) // Object Member의 method Referenece 사용(Object의 데이터들은 람다 파라미터로 모두 해결)
                // 파라미터가 하나인데 어떻게 이렇게 동작할까?
                // (bd1, bd2) -> bd1.compareTo(bd2) 에서 첫 파라미터의 메서드를 사용하고 두번째가 해당 메서드의 파라미터로 들어간다.
                .collect(toList());


        String targetString = "c";
        System.out.println(
                Arrays.asList("a", "b", "c", "d")
                        .stream()
                        // .anyMatch(s -> s.equals("c"))
                        // 여기는 입력 파라미터가 한개이다. 그러므로 String::equals와 같은 방법으로 사용할 수 없다.
                        .anyMatch(targetString::equals) // 특정 객체 Member의 method Refernece 사용(object는 정해진 데이터)
                // 하지만 반대로 targetString이라는 특정한 객체를 통해 equals 비교를하면 Method Reference를 사용할 수 있다.
        );

        methodReference03();
    }

    private static void methodReference03() {
        /* >>>>>>>>>>>>> First Class Function <<<<<<<<<<<<< */

        /* A Function can be passed as a parameter to another function. (함수의 파라미터에 함수를 넘기기) */
        System.out.println(
                "Using Lambda Expression: " +
                        testFirstClassFunction1(3, i -> String.valueOf(i * 2))
        );

        System.out.println(
                "Using Method Reference: " +
                        testFirstClassFunction1(3, EX1_MethodReferenceExamples::doubleThenToString)
                // method를 만들어 넘겨줄 수 있다. 그러므로 method를 first class citizen으로 만들 수 있게 된 것이다.
                // method를 만들어 놓으면 이를 재사용할 수 있으므로 더 효율적으로 사용될 수 있다.
                // method는 int를 받고 String을 리턴하는 메서드이기 때문에 조건에 주어진
                // Function<Integer, String>과 일치하므로 사용할 수 있는 것이다.
        );
        System.out.println("\n===========================================");

        /* A Function cab be returned as the result of another function. (함수의 리턴이 함수) */
        Function<Integer, String> fl = getDoubleThenToStringUsingLambdaExpression();
        System.out.println(fl.apply(3));

        Function<Integer, String> fmr = getDoubleThenToStringUsingMethodReference();
        System.out.println(fmr.apply(3));
        System.out.println("\n===========================================");

        /* A Function can be stored in the data structure */
        // Using Lambda Expression
        final List<Function<Integer, String>> fsl = Arrays.asList(i -> String.valueOf(i * 2), i -> String.valueOf(i * 3));
        for (final Function<Integer, String> f : fsl) {
            final String result = f.apply(3);
            System.out.println("result = " + result);
        }

        // Using Method Reference
        final List<Function<Integer, String>> fsMr = Arrays.asList(
                EX1_MethodReferenceExamples::doubleThenToString,
                EX1_MethodReferenceExamples::tripleThenToString
        );
        for (final Function<Integer, String> f : fsMr){
            final String result = f.apply(3);
            System.out.println("result = " + result);
        }
        System.out.println("\n===========================================");
        // Using Lambda Expression
        final Function<Integer, String> fl2 = i -> String.valueOf(i * 2);
        System.out.println("fl2.apply(3) = " + fl2.apply(3));

        // Using Method Reference
        Function<Integer, String> fsMr2 = EX1_MethodReferenceExamples::doubleThenToString;
        System.out.println("fsMr2.apply(3) = " + fsMr2.apply(3));

        System.out.println("\n===========================================");

        /* Both Lambda Expression and Method Reference */
        final List<Function<Integer, String>> fsBoth = Arrays.asList(
                i -> String.valueOf(i * 2),
                i -> String.valueOf(i * 3),
                EX1_MethodReferenceExamples::doubleThenToString,
                EX1_MethodReferenceExamples::tripleThenToString,
                EX1_MethodReferenceExamples::addHashPrefix
        );

        for (final Function<Integer, String> f : fsBoth){
            final String result = f.apply(3);
            System.out.println("result = " + result);
        }

        Function<String, String>[] functions;

    }

    private static String testFirstClassFunction1(int n, Function<Integer, String> f) {
        return "The result is " + f.apply(n);
    }

    private static String doubleThenToString(int i) {
        return String.valueOf(i * 2);
    }

    private static String tripleThenToString(int i) {
        return String.valueOf(i * 3);
    }

    private static String addHashPrefix(int i){
        return "#" + i;
    }

    private static Function<Integer, String> getDoubleThenToStringUsingLambdaExpression() {
        return i -> String.valueOf(i * 2); // function을 리턴
    }

    private static Function<Integer, String> getDoubleThenToStringUsingMethodReference() {
        return EX1_MethodReferenceExamples::doubleThenToString; // method를 리턴할 수 있다!
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}