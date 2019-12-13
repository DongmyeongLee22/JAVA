package main.Lambda;

/*
    함수형 인터페이스에는 오직 하나의 추상메서드만 정의되어야 한다. -> 람다식과 인터페이스의 메서드가 1:1로 연결되기 위해서..
    annotation을 붙이면 컴파일러가 확인해준다.
 */
@FunctionalInterface
public interface MyFunction {
    public abstract int max(int a, int b);
}
