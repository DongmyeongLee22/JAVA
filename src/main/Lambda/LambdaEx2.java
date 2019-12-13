package main.Lambda;

@FunctionalInterface
interface MyFunctionEx2 {
    void myMethod();
}

public class LambdaEx2 {

    public static void main(String[] args) {
        MyFunctionEx2 f = () -> {
        }; // MyF  f = (Myf) ( () -> {} );
        Object obj = (MyFunctionEx2) (() -> {
        }); // Object 타입으로 형변환 생략
        String str = ((Object) (MyFunctionEx2) (() -> {
        })).toString();

        System.out.println(f);
        System.out.println(obj);
        System.out.println(str);
        //System.out.println(() -> {}); // 람다식은 Obj타입으로 형변환 불가
        System.out.println((MyFunctionEx2) (() -> {
        }));
        //System.out.println((MyFunctionEx2) ( () -> {}).toString); // 에러
        System.out.println(((Object) (MyFunctionEx2) (() -> {
        })).toString());
    }

}
