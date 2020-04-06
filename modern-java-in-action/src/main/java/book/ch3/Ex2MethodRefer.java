package book.ch3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Ex2MethodRefer {
    public void test() {
        List<String> str = Arrays.asList("a", "b", "A");
        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        str.sort(String::compareToIgnoreCase);

        // 2
        Function<String, Integer> f = (s) -> Integer.parseInt(s);
        Function<String, Integer> f2 = Integer::parseInt;

        // 3
        Function<String, Boolean> isContainA = s -> this.containA(s);
        Function<String, Boolean> isContainB = this::containA;

    }

    private boolean containA(String s){
        return s.contains("A");
    }
}
