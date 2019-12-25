package me.sun.springdi.reflection2;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Book3Test {

    @Test
    void BookTest() throws Exception {
        Class<?> bookClass = Class.forName("me.sun.springdi.reflection2.Book");
        Constructor<?> constructor = bookClass.getConstructor(null);
        Book book = (Book) constructor.newInstance(null);
        System.out.println(book);
        System.out.println("-------------------------------------------------");

        // 생성자에 String.class 타입을 넣는다고 명시해주고 newInstance에 값을 넣으면 된다.
        Constructor<?> constructor2 = bookClass.getConstructor(String.class);
        Book book2 = (Book) constructor2.newInstance("TEST");
        System.out.println(book2);
        System.out.println("-------------------------------------------------");

        Field a = Book.class.getDeclaredField("A");
        System.out.println(a.get(null));// A는 static이라 어떤 인스턴스를 넘겨줄 수 없다.
        a.set(null, "AAAAA");
        System.out.println(a.get(null));
        System.out.println("-------------------------------------------------");

        Field b = Book.class.getDeclaredField("B");
        // 얘는 어떤 인스턴스가 있어야 값이 생기기 때문에 그냥 null 넣으면 안된다.
        b.setAccessible(true); // private인데 이걸 세팅하면 가져올 수 있다.
        System.out.println(b.get(book));
        b.set(book, "BBBBB");
        System.out.println(b.get(book));
        System.out.println("-------------------------------------------------");

        // Declared를 써야 public이 아닌 메서드 조회가능
        Method c = Book.class.getDeclaredMethod("c");
        // 인보크로 사용 가능, 두번째 매개변수엔 메서드의 파라미터 넣으면 되나 없으니깐 필요없음
        c.setAccessible(true);
        c.invoke(book);
        System.out.println("-------------------------------------------------");

        // sum은 파라미터가 있으니깐 타입 명시해줘야한다.
        Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book, 1, 2);
        System.out.println(invoke);

    }

}