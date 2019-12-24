package main.Lambda;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 최종연산에서 가장 유용하게 사용됨
public class Collect {

    // collect() 스트림의 최종연산, 매개변수로 컬랙터를 필요로함
    // Collector 인터페이스, 컬렉터는 이 인터페이스를 구현해야한다.
    // Collectors 클래스, static 메서드로 미리 작성된 컬렉터를 제공

    public static void main(String[] args) {

        Object[] objects = Stream.of(new String[]{"asd", "Asd", "Asd", "asd"}).toArray();
        // Students[] st1 = Stream.of(new String[]{"asd", "Asd", "Asd", "asd"}).toArray(); 에러
        //Students[] students = Stream.of(new String[]{"asd", "Asd", "Asd", "asd"}).toArray(Students[]::new);

        Student[] stuArr = {
                new Student("이자바", 3, 300),
                new Student("1이자바", 1, 200),
                new Student("2이자바", 2, 100),
                new Student("3이자바", 2, 150),
                new Student("4이자바", 1, 200),
                new Student("5이자바", 3, 290),
                new Student("6이자바", 3, 180)
        };

        List<String> names = Stream.of(stuArr).map(Student::getName).collect(Collectors.toList());

        Student[] students1 = Stream.of(stuArr).toArray(Student[]::new);

        Map<String, Student> stumap = Stream.of(stuArr).collect(Collectors.toMap(s -> s.getName(), p -> p));

        Long count = Stream.of(stuArr).collect(Collectors.counting());
        Integer total = Stream.of(stuArr).collect(Collectors.summingInt(Student::getTotalScore));

        Integer reducing = Stream.of(stuArr).collect(Collectors.reducing(0, Student::getTotalScore, Integer::sum));

        String collect = Stream.of(stuArr).map(Student::getName)
                .collect(Collectors.joining(",", "aa", "aa"));

        System.out.println(collect);


    }

}

class Students {
    String name;
    int clases;
    int point;

    public Students(String name, int clases, int point) {
        this.name = name;
        this.clases = clases;
        this.point = point;
    }
}
