package book.ch5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ex3Map {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("Hello", "World");

        List<String> collect = strings.stream()
                                              .map(word -> word.split(""))
                                              .flatMap(Arrays::stream)
                                              .distinct()
                                              .collect(Collectors.toList());

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        List<int[]> pairs2 = numbers1.stream()
                                    .flatMap(number1 -> numbers2.stream()
                                            .map(number2 -> new int[]{number1, number2}))
                                    .collect(Collectors.toList());
        pairs2.forEach(pair -> System.out.println(Arrays.toString(pair)));
        System.out.println("=======================================================");
        List<int[]> pairs = numbers1.stream()
                                    .flatMap(number1 -> numbers2.stream()
                                            .map(number2 -> new int[]{number1, number2}))
                                    .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                    .collect(Collectors.toList());

        pairs.forEach(pair -> System.out.println(Arrays.toString(pair)));

    }
}
