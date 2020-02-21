package book.ch2;

import java.util.*;
import java.util.function.Predicate;

public class Main {

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (Color.GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeigh(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventiry, ApplePredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventiry) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        List<Apple> heavyApples = filterApples(inventory, apple -> apple.getWeight() > 100);
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        List<Apple> redApples = filter(inventory, apple -> Color.RED.equals(apple.getColor()));
        List<Integer> greaterthanTen = filter(integerList, i -> i > 10);
        List<String> longerthanTen = filter(stringList, s -> s.length() > 10);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}

enum Color {
    GREEN, RED
}

class Apple {
    private Color color;
    private int weight;

    public int getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}

interface Apredicate<T> {
    boolean test(T t);
}

interface ApplePredicate {
    boolean test(Apple apple);
}

class AppleHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}

class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return Color.GREEN.equals(apple.getColor());
    }
}

class AppleRedColorAndHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() >= 100 && Color.RED.equals(apple.getColor());
    }
}