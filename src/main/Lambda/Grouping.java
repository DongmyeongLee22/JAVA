package main.Lambda;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

// colect는 주로 그룹화할때 사용한다.
public class Grouping {
    public static void main(String[] args) {
        Person[] stuArr = {
                new Person("나자바", true, 1, 1, 300),
                new Person("김지미", false, 1, 1, 250),
                new Person("김자바", true, 1, 1, 200),
                new Person("이지미", false, 1, 2, 150),
                new Person("남자바", true, 1, 2, 100),
                new Person("인지미", false, 1, 2, 50),
                new Person("황지미", false, 1, 3, 100),
                new Person("강지미", false, 1, 3, 150),
                new Person("이자바", true, 1, 3, 200),

                new Person("나자바", true, 2, 1, 300),
                new Person("김지미", false, 2, 1, 250),
                new Person("김자바", true, 2, 1, 200),
                new Person("이지미", false, 2, 2, 150),
                new Person("남자바", true, 2, 2, 100),
                new Person("인지미", false, 2, 2, 50),
                new Person("황지미", false, 2, 3, 100),
                new Person("강지미", false, 2, 3, 150),
                new Person("이자바", true, 2, 3, 200)
        };

        // =================================== partitioningBy ===================================
        // ===================== 단순 성별 분할
        Map<Boolean, List<Person>> stuBySex = Stream.of(stuArr).collect(partitioningBy(Person::isInMale));
        List<Person> male = stuBySex.get(true);
        List<Person> female = stuBySex.get(false);
        System.out.println("male = " + male);
        System.out.println("female = " + female);

        // ===================== 단순 성별 분할 후 카운트
        Map<Boolean, Long> counting = Stream.of(stuArr).collect(partitioningBy(Person::isInMale, counting()));
        Long maleCount = counting.get(true);
        Long femaleCount = counting.get(false);
        System.out.println("maleCount = " + maleCount);
        System.out.println("femaleCount = " + femaleCount);

        // ===================== 단순 성별 분할 후 통계 성별 중 1등
        Map<Boolean, Optional<Person>> topScoreBy = Stream.of(stuArr)
                .collect(partitioningBy(Person::isInMale, maxBy(comparingInt(Person::getScore))));
        Optional<Person> maleTop = topScoreBy.get(true);
        Optional<Person> femaleTop = topScoreBy.get(false);
        System.out.println("maleTop = " + maleTop.get());
        System.out.println("femaleTop.get() = " + femaleTop.get());

        // ===================== 단순 성별 분할 후 통계 성별 중 1등 Optional 제거
        Map<Boolean, Person> notOptional = Stream.of(stuArr)
                .collect(partitioningBy(Person::isInMale,
                        collectingAndThen(
                                maxBy(comparingInt(Person::getScore)),
                                Optional::get
                        )
                ));
        Person maleTopP = notOptional.get(true);
        Person femaleTopP = notOptional.get(false);
        System.out.println("maleTopP = " + maleTopP);
        System.out.println("femaleTopP = " + femaleTopP);

        // =================================== groupingBy ===================================
        // ------------------ 반별로 그룹화 ------------------
        Map<Integer, List<Person>> stuGrade = Stream.of(stuArr).collect(groupingBy(Person::getGrade));

        // ------------------ 성적별 그룹화 ------------------
        Map<Person.Level, List<Person>> Bylevel = Stream.of(stuArr).collect(
                groupingBy(s -> {
                    if (s.getScore() >= 200) return Person.Level.HIGH;
                    else if (s.getScore() >= 100) return Person.Level.MID;
                    else return Person.Level.LOW;
                }));
        List<Person> people = Bylevel.get(Person.Level.HIGH);

        // ------------------ 성적별 학생수 카운트 ------------------
        Map<Person.Level, Long> countingST = Stream.of(stuArr).collect(
                groupingBy(s -> {
                    if (s.getScore() >= 200) return Person.Level.HIGH;
                    else if (s.getScore() >= 100) return Person.Level.MID;
                    else return Person.Level.LOW;
                }, counting()));

        // ------------------ 다중 그룹화 (학년별, 반별) ------------------
        Stream.of(stuArr)
                .collect(
                        groupingBy(Person::getBan,
                                groupingBy(Person::getGrade))
                );

        // ------------------ 다중 그룹화 (학년별, 반별)  1등------------------
        Stream.of(stuArr)
                .collect(
                        groupingBy(Person::getBan,
                                groupingBy(Person::getGrade,
                                        collectingAndThen(
                                                maxBy(comparingInt(Person::getScore))
                                                , Optional::get
                                        )
                                ))
                );


    }
}


class Person {

    String name;
    boolean inMale;
    int grade;
    int ban;
    int score;

    public Person(String name, boolean inMale, int grade, int ban, int score) {
        this.name = name;
        this.inMale = inMale;
        this.grade = grade;
        this.ban = ban;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", inMale=" + inMale +
                ", grade=" + grade +
                ", ban=" + ban +
                ", score=" + score +
                '}';
    }

    public String getName() {
        return name;
    }

    public boolean isInMale() {
        return inMale;
    }

    public int getGrade() {
        return grade;
    }

    public int getBan() {
        return ban;
    }

    public int getScore() {
        return score;
    }

    enum Level {
        HIGH, MID, LOW
    }
}