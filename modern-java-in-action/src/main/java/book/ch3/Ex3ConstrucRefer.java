package book.ch3;

import java.util.function.Function;
import java.util.function.Supplier;

public class Ex3ConstrucRefer {
public static void main(String[] args) {
    // 기본 생성자
    Supplier<Temp> t = () -> new Temp();
    Supplier<Temp> t2 = Temp::new;

    // 파라미터가 있는 생성자
    Function<String, Temp> t3 = s -> new Temp(s);
    Function<String, Temp> t4 = Temp::new;
}

static class Temp{
    private String name;

    public Temp() {
    }

    public Temp(String name) {
        this.name = name;
    }
}
}
