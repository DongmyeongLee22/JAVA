package chapter1.item1.other;

import chapter1.item1.temp.Item;

import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        Item item = Item.createItem("Dexter");
        item.print();
        // Item item1 = new Book("Dexter");  생성 불가
    }
}
