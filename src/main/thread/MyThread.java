package main.thread;

public class MyThread extends Thread {
    @Override
    public void run() {
    }
}


/*
Thread를 상속받으면 다른 클래스 상속 불가하므로 인터페이스로 구현하는것이 일반적이다.
Runnable 인터페이스 구현 방법은 재사용성이 높고 코드의 일관성을 유지할 수 있으므로 보다 객체지향적인 방법이다.
 */
class MyThread2 implements Runnable {
    @Override
    public void run() {

    }
}

