package main.Lambda;

/* 람다식이란?
    메서드를 하나의 '식(expression)'으로 표현한 것이다. 람다식은 함수를 간략하면서도 명확한 식으로 표현할수 있게 해준다.
    메서드의 이름과 반환값이 없으므로 익명함수(anonymous function)이라고도 한다.

 */
public class Lambda {

    MyFunction f = (a, b) -> a > b ? a : b;
    int max = f.max(3, 3);

}
