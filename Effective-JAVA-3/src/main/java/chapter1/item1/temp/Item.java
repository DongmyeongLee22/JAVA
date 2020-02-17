package chapter1.item1.temp;

public interface Item {

    void print();

    static Item createItem(String name){
        return new Book(name);
    }
}

class Book implements Item
{
    String name;

    Book(String name){
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println(name);
    }
}
