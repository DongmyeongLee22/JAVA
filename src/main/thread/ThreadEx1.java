package main.thread;

public class ThreadEx1 {

    /*
    main도 또 하나의 쓰레드이다. main 메서드가 수행을 마쳤더라도 다른 쓰레드가 아직 작업중이라면 프로그램은 종료되지 않는다.
    즉 실행 중인 사용자 쓰레드가 하나도 없을 떄 프로그램은 종료된다.
     */
    public static void main(String[] args) {

        ThreadEx1_1 t1 = new ThreadEx1_1();

        Runnable runnable = new ThreadEx1_2();
        Thread t2 = new Thread(runnable);

        Thread test1 = new Thread(runnable, "Test1");
        Thread test2 = new Thread(runnable);
        test2.setName("Test2");

        t1.start();
        t2.start();
        test1.start();
        test2.start();

        // 한 번 실행이 종료된 쓰레드는 다시 실행 불가하다.
        // t1.start(); 에러 발생
        t1 = new ThreadEx1_1();
        t1.start();
    }



}

class ThreadEx1_2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            // Runnable은 run()만 존재하므로 currentThread().getName으로 쓰레드 이름을 받아올 수 있다.
            System.out.println(Thread.currentThread().getName());
        }
    }
}

class ThreadEx1_1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            // Thread를 상속받으면 간단히 getName으로 호출이가능
            System.out.println(getName());
        }
    }
}
