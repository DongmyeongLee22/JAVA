package me.sun.springdi.re;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MyBookTest {


    @Test
    void reTest() throws Exception {
        // 클래스를 로딩만해도 클래스타입의 인스턴스가 만들어진다.
        Class<Book> bookClass = Book.class;

        // 이미 인스턴스를 만들었다면 그것을 통해 서도 접근이 가능
        Book book = new Book("c", "c", "c");

        Class<? extends Book> aClass = book.getClass();

        // 이렇게 문자열로도 가능하다. 없으면 ClassNotFound Exception
        // 스프링에서 이런식의 문자열이 있다면 어디선가는 이렇게 Class.forName으로 참조될 확률이 높다.
        Class<?> aClass1 = Class.forName("me.sun.springdi.re.Book");

        // 이렇게 클래스를 통해 필드들을 가져올 수도 있다,
        // 이렇게 출력해보면 public인 d만 나옴.
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);

        System.out.println("\n==========================getDeclaredFields==============================");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        System.out.println("\n============================getDeclaredFields============================");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true);
                System.out.printf("%s || %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });


        System.out.println("\n==========================getMethods==============================");
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

        System.out.println("\n==========================getDeclaredMethods==============================");
        Arrays.stream(bookClass.getDeclaredMethods()).forEach(System.out::println);

        System.out.println("\n==========================getEnclosingMethod==============================");
        System.out.println(bookClass.getEnclosingMethod());

        System.out.println("\n=======================getConstructors=================================");
        Arrays.stream(bookClass.getConstructors()).forEach(System.out::println);


        System.out.println("\n========================getSuperclass================================");
        System.out.println(MyBook.class.getSuperclass());

        System.out.println("\n=========================getInterfaces===============================");
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);

        System.out.println("\n======================getDeclaredFields==================================");
        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
            // modifiers를 받아서 여러가지 정보들을 알 수 있다.
            // Field뿐만 아니라 Method로도 가능하다.
            int modifiers = f.getModifiers();
            System.out.println(f);
            System.out.println("private: " + Modifier.isPrivate(modifiers));
            System.out.println("static: " + Modifier.isStatic(modifiers));
            System.out.println("proteced: " + Modifier.isProtected(modifiers));
        });


    }

}