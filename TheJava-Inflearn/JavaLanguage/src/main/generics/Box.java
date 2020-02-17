package main.generics;

/* 지네릭스는 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에 컴파일 시의 타입체크를 해주는 기능이다.
 *  장점
 *  - 타입 안정성을 제공
 *  - 타입체크와 형변환을 생략할 수 있어 코드 간결
 *  */
public class Box<T> {
    T item;

    public static void main(String[] args) {
        Box<Apple> appleBox = new Box<Apple>();
        // Box<Apple> appleBox2 = new Box<Grape>(); <>안은 무조건 같아야 함

        // 이렇게는 가능
        Box<Apple> appleBox2 = new Fruit<Apple>();

        /*
        <? extends T> 는 T의 자손만 가능
        <? super T> 는 T의 조상만 가능
         */
        /*
        Comparator<? super T>인 이유는 만약 <T>라면 모든 클래스에 맞는 Comparator를 구현해야한다.
        예를들어 Apple 의 조상이 Fruit일 때 Fruit의 필드를 Apple은 가지므로 Apple을 정렬할 때
        Fruit의 필드로도 가능하다 그렇기 때문에 super로 만들어짐
         */

        /* 지네릭 메서드
            - 메서드 선언부에 지네릭 타입이 선언된 메서드
            - static <T> void sort(List<T> list, Comparator<? super T> c);

            static Juice makeJuice(FruitBox<? extends Fruit> box)
            위를 아래와같이 지네릭 메서드로 만들 수 있다.

            static <T extends Fruit> Juice makeJuice(FruitBox<T> box)
         */


    }

    void setItem(T item) {
        this.item = item;
    }
}

class Fruit<T> extends Box {

}

class Apple {
}

class Grape extends Apple {

}