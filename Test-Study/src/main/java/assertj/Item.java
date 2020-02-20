package assertj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
    String name;
    int price;
    Person producer;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name) {
        this.name = name;
    }
}

@Getter
@AllArgsConstructor
class Person{
    String name;
}
