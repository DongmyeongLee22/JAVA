package chapter1.item3;

import java.io.Serializable;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/25
 */
public class SingleTon implements Serializable {
    private static final SingleTon INSTANCE = new SingleTon();
    private SingleTon(){}

    public static SingleTon getInstance(){
        return INSTANCE;
    }

}
