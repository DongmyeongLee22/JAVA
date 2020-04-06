package book.ch3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Ex1 {
    public static void main(String[] args) {
        IntPredicate p1 = (int i) -> i % 2 == 0;
        // 박싱이 없어 메모리 효율적

        Predicate<Integer> p2 = (Integer i) -> i % 2 == 0;
        // 박싱하기 때문에 힙 영역어 저장되어 메모리 비효율적
    }

    public void temp(){
        List<Integer> list = new ArrayList<>();

        Predicate<Integer> p =  i -> list.add(i);
        Consumer<Integer> p2 = i -> list.add(i);
    }


}
