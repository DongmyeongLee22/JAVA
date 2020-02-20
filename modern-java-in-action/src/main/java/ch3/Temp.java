package ch3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Temp {
    public String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    public String processFile(BufferReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

    public void temp() throws IOException {
        String oneLine = processFile(br -> br.readLine());
        String twoLines = processFile(br -> br.readLine() + br.readLine());
        boolean fileter = fileter("Hello", s -> s.equals("Hello"));
    }

    public static <T> void print(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f){
        List<R> ret = new ArrayList<>();
        for (T t : list) {
            ret.add(f.apply(t));
        }
        return ret;
    }

    public static void main(String[] args) {
        List<String> strings = List.of("hello", "dexter");
        List<String> map = map(strings, s -> s.toUpperCase()); // HELLO, DEXTER
    }

    public <T> boolean fileter(T data, Predicate<T> p) {
        return p.test(data);
    }

}

@FunctionalInterface
interface BufferReaderProcessor {
    String process(BufferedReader br) throws IOException;
}
