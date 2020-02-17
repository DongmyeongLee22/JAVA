package main.Lambda;

import java.util.Optional;

public class OptinalEx {
    public static void main(String[] args) {

        // Optional<String> o1 = Optional.of(null);  NullPointerException 발생 아래와같이 해야함

        Optional<String> o2 = Optional.ofNullable(null);
        Optional<String> o3 = Optional.empty();
        Optional<String> empty = Optional.<String>empty();

        String s = o3.orElseGet(String::new);
        String s1 = o3.orElseThrow(NullPointerException::new);

        // 스트림 처럼 map, filter 등 사용 가능
        o3.map(Integer::new).orElseThrow(NullPointerException::new);

        // null 이면 false,
        if (o3.isPresent()) {
            String s2 = o3.get();
        }


    }
}
