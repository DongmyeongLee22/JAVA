package youtube.e1_first;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        String result = integers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" : "));
        System.out.println(result);
    }
}
