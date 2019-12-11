package main.thread;

public class ThreadEx3 {
    public static void main(String[] args) {
        ThreadEx3_1 threadEx3_1 = new ThreadEx3_1();
        threadEx3_1.run();
    }

    /* run()
    실행 결과를 보면 이제는 main이 보인다. 즉 run은 쓰레드 생성을 하는게 아니라 단순히 선언된 메서드를 호출하는 것
     */
}

class ThreadEx3_1 extends Thread{

    public void run(){
        throwException();
    }

    public void throwException(){
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
