package youtube.e4_custom_function;

/**
 * Created by Stranger on 2020/02/22
 */

/**
 * 커스텀하여 함수형 인터페이스를 만들어보았다.
 */
public class CustomFunctionExmaple {

    public static void main(String[] args) {
        println("Hello", 300, "Man", (s1, i, s2) -> s1 + " " + i + " " + s2);

        println(1, 2, 3, (i1, i2, i3) -> ((Integer)(i1 + i2 + i3)).toString());
        // + 연산자가 들어가면 언박싱을 통해 int로 변환되므로 toString이 불가능하다.

        println(1, 2, 3, (i1, i2, i3) -> String.valueOf(i1 + i2 + i3));
        // 좀 더 안전한 방식
    }

    private static <T1, T2, T3> void println(T1 t1, T2 t2, T3 t3, Function3<T1, T2, T3, String> function) {
        System.out.println(function.apply(t1, t2, t3));
    }
}

@FunctionalInterface
interface Function3<T1, T2, T3, R> {
    R apply(T1 t1, T2 t2, T3 t3);
}
