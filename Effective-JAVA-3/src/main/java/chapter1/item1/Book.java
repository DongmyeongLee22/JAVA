package chapter1.item1;

import com.sun.tools.javac.util.List;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Book {
    protected Book() {

    }

    public static void main(String[] args) {
    }

    static Book createBook() {
        return new Book();
    }
}

class Child extends Book {

}
