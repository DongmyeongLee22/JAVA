package youtube.e7_nineth;

/**
 * Created by Stranger on 2020/02/23
 */
public class EX2_ClosureExamples2 {

    private int number = 999;

    public static void main(String[] args) {
        test();
    }

    private static void test(){
        int number = 100;

        // 람다 표현식을 쓰면 컴파일 후 바이트 코드단에서는 어떻게 표현될까?

        Runnable runnable = new Runnable() {
            // Anonymous Class는 컴파일 후 class 파일을 보면 EX2_ClosureExamples2$1.class 이러한 클래스 파일이 추가로 생겨난다.
            // 즉 Anonymous Class가 생성될 때마다 새로운 class 파일이 생성된다.
            // Lambda Expression은 어떠한 class 파일도 추가로 생성되지 않는다.
            // 레시피가 추가되어 런타임에 invoke dynamic에 의해 LambdaMetaFactory가 알맞게 생성 해준다.

            @Override
            public void run() {
                System.out.println(number);
            }
        };
        runnable.run();

        // javap -c -p EX2_ClosureExamples2로 자세하게 바이트코드를 볼 수 있다.
        // 람다표현식의 경우 따로 class가 생겨나는게 아닌 람다 레시피가 생성된다.
        // 결국 람다식도 객체를 사용하는것 이기때문에 객체가 존재하여야 한다.
        // 람다 레시피가 런타임시점에서 해당 객체들을 생성하게 되는 것이다.

        // Invoke Dynamic -> 동적 언어를 지원하기 위해 자바7에서 생겨났다.
        // JAVA는 정적타입 언어이므로 컴파일 시점에서 타입이 다 결졍된다.
        // 하지만 JVM에서 도는 그루비 같은 동적언어는 컴파일 타임에 타입을 결정할 수 없는게 존재한다.
        // 그렇기 때문에 이를 지원하기 위해 Invoke Dynamic을 지원하여 런타임시에 타입이 결정되지 않은 타입을 결정할 수 있게 해준다.

        // Method Handle -> 메서드를 제어할 수 있는 기능
        // 어떤 메서드를 저장하고, 원할 때 꺼내서 쓸 수 있게 해준다.
        // JVM이 돌면서 런타임에 생성이 필요한 것들을 보고, bootstrap을 이용하여 type을 생성한다.
        // 람다의 경우 bootstrap이 LambdaMetaFactory이다.
        // 그러므로 LambdaMetaFactory에게 알맞는 객체를 Invoke Dynamic을 통해 요청하여 해당 객체를 받게 된다.

        /* javap -c -p EX2_ClosureExamples2
        - 람다표현식이 컴파일될 때 컴파일러가 desugar를 하여 lambda body에 해당하는 코드로 메서드가 생성된다.
        - 이 메서드가 메서드 핸들에의해 바로 실행할 수 있게 된다.
        - 이 메서드에는 외부 스코프에 접근한 number를 파라미터로 가지고 있다.

        private static void lambda$test$0(int);
         Code:
             0: getstatic     #8                  // Field java/lang/System.out:Ljava/io/PrintStream;
             3: iload_0
             4: invokevirtual #9                  // Method java/io/PrintStream.println:(I)V
             7: return

        - 만약 어떠한 상태를 가지지 않는다면(i -> i * 2 와 같은) 해당 객체만 생성해두고 LambdaMetaFactory는 계속해서
          그 객체를 재사용(캐싱)하므로 최고 60배 가량 빠르다.
        - 하지만 지금처럼 외부 값을 참조하는 클로저이기 때문에 상태가 없는 람다식보다 느리겠지만 익명클래스랑은 같으므로 속도 차이가 없다.
        - map, filter 등에서 사용하는 predicate, function등은 상태가 없기 때문에 캐싱을 하므로 매우 속도 효율이 있다.
        - 그러므로 여러모로 람다식이 쓸데없는 클래스파일을 생성하지 않고, 성능도 뛰어나며, 가독성도 좋고, 스코프가 없어 클로저를 더욱 더 안전하게 사용할 수 있게된다.

        - 레시피만 존재하고 타입, 객체가 다 런타임 시점에 생성되므로 컴파일 시점에 클래스가 존재하지 않는 것이다.
        - 그리고 한 번 생성된것을 딱 보고 똑같은게 또 요청이 들어오면 저장된 객체를 바로 리턴해준다.

        - 만약 test method의 number를 삭제하면 객체의 numbe를 참조하므로 메서드는 static이 아닌 void로 생성되게 된다.
         */
        Runnable runnable1 = () -> System.out.println(number);
        runnable1.run();
    }
}
