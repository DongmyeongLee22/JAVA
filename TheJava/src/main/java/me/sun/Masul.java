package me.sun;

import java.io.IOException;

public class Masul {
    public static void main(String[] args) throws IOException {
        /* 바이트 코드 조작
        .class보면 바이트코드가 변경된것을 알 수 있다.
         */
//        new ByteBuddy().redefine(Moja.class)
//                .method(named("pullOut")).intercept(FixedValue.value("Rabbit!!"))
//                .make().saveIn(new File("C:\\Users\\dmdsj\\MyProject\\javalanguage\\TheJava\\target\\classes"));
//
        System.out.println(new Moja().pullOut());
    }
}
