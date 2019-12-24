package me.sun;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Masul {
    public static void main(String[] args) throws IOException {
        /* 바이트 코드 조작
        .class보면 바이트코드가 변경된것을 알 수 있다.

         */

        // 이렇게 classloader를 이용하면 ByteBuddy에서 Moja를 호출하지 않아 출력을 동시에 해도 적용 된다.
//        ClassLoader classLoader = Masul.class.getClassLoader();
//        TypePool typePool = TypePool.Default.of(classLoader);
//        new ByteBuddy().redefine(
//                typePool.describe("me.sun.Moja").resolve(),
//                ClassFileLocator.ForClassLoader.of(classLoader)
//        )
//                .method(named("pullOut")).intercept(FixedValue.value("Rabbit!!"))
//                .make().saveIn(new File("C:\\Users\\dmdsj\\MyProject\\javalanguage\\TheJava\\target\\classes"));

//        new ByteBuddy().redefine(Moja.class)
//                .method(named("pullOut")).intercept(FixedValue.value("Rabbit!!"))
//                .make().saveIn(new File("C:\\Users\\dmdsj\\MyProject\\javalanguage\\TheJava\\target\\classes"));

        System.out.println(new Moja().pullOut());
    }
}
