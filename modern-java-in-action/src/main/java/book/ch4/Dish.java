package book.ch4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString(of = "name")
public class Dish {
    private final String name;
    private final boolean vegitarian;
    private final int calories;
    private final Type type;

    public enum Type{MEAT, FIST, OTHER}

    public static List<Dish> makeDishes(){
        return Arrays.asList(
                new Dish("port", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french", false, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FIST),
                new Dish("slamon", false, 450, Type.FIST)
        );
    }
}
