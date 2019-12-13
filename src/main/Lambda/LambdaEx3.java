package main.Lambda;

@FunctionalInterface
interface MyFucEx3 {
    void myMethod();
}

class Outer {
    int val = 10;

    class Inner {
        int val = 20;

        void method(int i) {
            int val = 30; // final int val = 30;
            //i = 10; 상수값 변경 불가 -> final int i;

            MyFucEx3 f = () -> {
                System.out.println("i = " + i);
                System.out.println("val = " + val);
                System.out.println("++this.val = " + ++this.val);
                System.out.println("Outer.this.val = " + Outer.this.val);
            };
        }
    }
}

public class LambdaEx3 {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.method(100);

        /*
        람다식 내에서의 지역 변수는 변경이 불가하다.
        허나 Inner Outer의 인스턴스 변수는 상수로 간주되지 않는다.
        외부 지역 변수랑 이름이 같으면 안된다.
         */
    }
}
