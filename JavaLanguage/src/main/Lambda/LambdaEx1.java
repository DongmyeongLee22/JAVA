package main.Lambda;

@FunctionalInterface
interface MyFunction2 {
    void run();
}

public class LambdaEx1 {

    static void excecute(MyFunction2 f) { // 매개 변수 타입이 MyFu인 메서드
        f.run();
    }

    static MyFunction2 getMyFunction() { // 반환 타입이 MyF인 메서드
        MyFunction2 f = () -> System.out.println("f3.run()");
        return f;
    }

    public static void main(String[] args) {
        MyFunction2 f1 = () -> System.out.println("f1.run()"); // 람다식으로 구현

        MyFunction2 f2 = new MyFunction2() {
            @Override
            public void run() {
                System.out.println("f2.run()");
            }
        };

        MyFunction2 f3 = getMyFunction();

        f1.run();
        f2.run();
        f3.run();

        excecute(f1);
        excecute(() -> System.out.println("run()"));
    }
}

