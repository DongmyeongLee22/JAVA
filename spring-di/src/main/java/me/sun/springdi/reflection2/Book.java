package me.sun.springdi.reflection2;

public class Book {

    public static String A = "DD";

    private String B = "B";

    public Book() {
    }

    public Book(String b) {
        B = b;
    }

    private void c() {
        System.out.println("c");
    }

    public int sum(int left, int right) {
        return left + right;
    }

}
