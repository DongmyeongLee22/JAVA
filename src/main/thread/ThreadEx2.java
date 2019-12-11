package main.thread;

public class ThreadEx2 {
    public static void main(String[] args) {
        ThreadEx2_1 threadEx2_1 = new ThreadEx2_1();
        threadEx2_1.start();
    }

    /* start()
    ThreadEx2_1가 예외를 발생시켜도 main 메서드 쓰레드에는 영향을 미치지 않기때문에 결과에 나오지 않는다.
    즉 쓰레드를 만들었다는 뜻!
     */
}

class ThreadEx2_1 extends Thread{

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
