package main.Lambda;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamEx3 {


    public static void main(String[] args) {
        Stream<Student> studentStream = Stream.of(
                new Student("이자바", 3, 300),
                new Student("이자바", 1, 200),
                new Student("이자바", 2, 100),
                new Student("이자바", 2, 150),
                new Student("이자바", 1, 200),
                new Student("이자바", 3, 290),
                new Student("이자바", 3, 180)
        );

        // 반별 정렬 후 기본정렬(Cpmparable에 구현한 점수 내림차순)
        studentStream.sorted(Comparator.comparing(Student::getBan)
                .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

}

class Student implements Comparable<Student> {
    String name;
    int ban;
    int totalScore;

    public Student(String name, int ban, int totalScore) {
        this.name = name;
        this.ban = ban;
        this.totalScore = totalScore;
    }

    public String getName() {
        return name;
    }

    public int getBan() {
        return ban;
    }

    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", ban=" + ban +
                ", totalScore=" + totalScore +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return o.totalScore - this.totalScore;
    }
}
