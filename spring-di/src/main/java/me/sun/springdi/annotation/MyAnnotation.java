package me.sun.springdi.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited // 이걸 붙여주면 Book의 하위클래스인 MyBook에서 Anno가 조회된다.
public @interface MyAnnotation {

    //annotation의 필드는 프리미티브 타입이나 프리티타입의 레퍼런스타입만 가능
    //String name();

    // 이렇게 기본값 주면 Annotation 붙이는곳에 값을 안줘도된다.
    String name() default "master";

    int number() default 100;

    // value로 하면 @Anno("hi") 이렇게 바로 해도된다
    // 하지만 다른 것들이 있으면 @Anno(number = 100, value= = "10") 이런식으로 해야한다.
    String value() default "asasa";

}
