package me.sun.springdi.re;

public class Book {

    private static final String C = "BOOK";
    private static String B = "BOOK";
    public String d = "d";
    protected String e = "e";
    private String a;

    public Book() {

    }

    public Book(String a, String d, String e) {
        this.a = a;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("g");
    }

    public int h() {
        return 100;
    }
}
