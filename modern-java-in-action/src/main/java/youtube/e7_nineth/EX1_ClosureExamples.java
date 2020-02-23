package youtube.e7_nineth;

/**
 * Created by Stranger on 2020/02/23
 */
public class EX1_ClosureExamples {

    private int number = 999;

    public static void main(String[] args) {
        new EX1_ClosureExamples().test1();
    }

    private void test1() {
        // 원래 이게 final이 아니라면 Runnable의 anonymous class에서 접근을 할 수 없었다.
        // 허나 자바8에서는 가능하다. 하지만 number를 변경하려고하면 에러가 나타난다.
        // Effectiviely Final로 편의를 위해서 final을 생략해 준것일 뿐이다.

        // number를 다른 메서드에게 넘겨줄때 number를 넘겨주는게 아닌 100이라는 값을 넘겨준다.
        // 이 값이 또 다른 쓰레드로 넘어갈 지 사용될지 알 수 가 없다.
        // Race condition 문제를 막기위해 final이 필요한 것이다.

        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // number = 3; Error!
                System.out.println(number);
                // Annoymous Class는 this가 Runnable이 되기 때문에 this로 바로 접근이 불가능하다.
                System.out.println(EX1_ClosureExamples.this.number);
            }
        });

        // number = 3; number of run Method Error!

        // lambda expression는 스코프가 존재하지 않기 때문에 해당 this로 runnable에 접근할 수 없다. 그러므로 this 에러가 발생하지 않는다. JS ES6 Arrow Function 효과랑 같은듯
        testClosure("Lambda", () -> System.out.println(this.number));

    }

    private void test2() {
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(EX1_ClosureExamples.this.toString());
            }
        });

        testClosure("Lambda", () -> System.out.println(this.toString()));
    }

    public static <T> String toString(T value) {
        return "The value is " + String.valueOf(value);
    }

    private void test3() {
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // error 발생
                // anonymous class에서는 anonymous clas가 가지고 있는 메서드와 이름이 동일한 외부 메서드에 접근할 경우 shadowing이 발생하여 사용이 불가능하다.(this를 따로 설정해주면 된다.)
                // 허나 lambda expression은 scope가 없기 때문에 자기 자신만의 메서드가 존재하지 않으므로 문제가 발생하지 않는 것이다.
                // System.out.println(toString("Test"));
            }
        });
        // error 안남
        testClosure("Lambda", () -> toString("Test"));
    }


    private void test4() {
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                int number = 50; // shadowing에 의해 외부의 number를 볼 수 없다.

                // 이 경우에는 number가 어디 number가 적용되는지 알 수 없다.
                // 모든 상황을 스스로가 통제할 수 있으면 좋지만 유지보수 상황에서는 이러한 일이 쉽지 않다.
                // 그러나 람다식의 경우 스코프가 없어서 확장되기 때문에 같은 변수의 값을 사용할 수 없으므로 더 안전하게 개발이 가능하다.
                int x = number + 100;
                System.out.println(number);
            }
        });

        // error 안남
        testClosure("Lambda", () -> {
            // int number = 50; Variable 'number' is already defined in the scope -> Error Description
            // error description을 보면 already defined in the scope인 것을 알 수 있다. 즉 해당 Lambda Expression에는 Scope를 가질 수 없는 것이다.

            System.out.println(this.number);
        });
    }

    // void 메서드는 테스트하기 용이하지 않다. 안에서 무슨 일이 일어나는지 알 수 없기떄문에 사용하지 않는게 좋다.
    private static void testClosure(final String name, final Runnable runnable) {
        System.out.println("=================================");
        System.out.println(name + ": ");
        runnable.run();
        System.out.println("=================================");
    }
}
