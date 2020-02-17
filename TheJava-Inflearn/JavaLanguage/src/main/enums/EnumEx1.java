package main.enums;

enum Direction {
    EAST(1, ">"), SOUTH(2, "V"), WEST(3, "<"), NORTH(4, "^");

    private static Direction[] DIR_ARR = Direction.values();

    private int value;
    private String symbol;

    Direction(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    } // 묵시적으로 private이라 외부에서 사용 불가하다.

    public static Direction of(int dir) throws IllegalAccessException {
        if (dir < 1 || dir > 4) {
            throw new IllegalAccessException("Invalid value:" + dir);
        }
        return DIR_ARR[dir - 1];
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public Direction rotate(int num) {
        num = num % 4;

        if (num < 0) num += 4;

        return DIR_ARR[(value - 1 + num) % 4];
    }

    @Override
    public String toString() {
        return "Direction{" +
                "value=" + value +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}

public class EnumEx1 {
    public static void main(String[] args) {
        Direction east = Direction.EAST;
        Direction west = Direction.valueOf("WEST");
        Direction east1 = Enum.valueOf(Direction.class, "EAST");
        System.out.println("east = " + east);
        System.out.println("west = " + west);
        System.out.println("east1 = " + east1);

    }
}
