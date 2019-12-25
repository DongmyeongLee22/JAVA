package me.sun.springdi.annotation;

import me.sun.springdi.annotation.Book;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BookAnnoTest {

    @Test
    void AnnoTest() throws Exception {
        // 기본적으로 Annotation은 주석이랑 마찬가지이므로 아무것도 하지 않고 Annotaiton을 추가하면 출력이 안된다.
        // 기본 값이 CLASS라서 그렇고 RUNTIME으로 바꾸면 됨
        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);
        System.out.println("====================================================================================\n");
        // 부모의 Anno에 Inherited가 있으면 조회됨
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);
        System.out.println("====================================================================================\n");
        // Declared면 자기꺼만 조회
        Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println);
        System.out.println("====================================================================================\n");

        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
            Arrays.stream(f.getAnnotations()).forEach(a -> {
                if (a instanceof FieldAnnotation) {
                    FieldAnnotation fieldAnnotation = (FieldAnnotation) a;
                    System.out.println(fieldAnnotation.value());

                }
            });
        });
        System.out.println("====================================================================================\n");

    }


}