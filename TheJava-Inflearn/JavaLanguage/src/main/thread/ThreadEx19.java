package main.thread;
/* join(): 다른 쓰레드의 작업을 기다린다.
쓰레드 자신이 하던 작업을 잠시 멈추고 다른 쓰레드가 지정된 시간동안 작업을 수행하도록 할 때 join()을 사용한다.

시간을 지정하지 않으면 해당 쓰레드가 작ㅇ버을 모두 마칠 때 까지 기다리게된다. 작업 중에 다른 쓰레드의 작업이 먼저 수행
되어야 할 필요가 있을떄 join을 사용

sleep과 다르게 join은 현재 쓰레드가 아닌 특정 쓰레드에 대해 동작하므로 static메서드가 아니다.
 */
public class ThreadEx19 {

    static long startTime = 0;

    public static void main(String[] args) {
        ThreadEx19_1 th1 = new ThreadEx19_1();
        ThreadEx19_2 th2 = new ThreadEx19_2();
        th1.start();
        th2.start();

        startTime = System.currentTimeMillis();

        try {
            th1.join(); // main 쓰레드가 th1의 작업을 끝날 때까지 기다린다.
            th2.join(); // main 쓰레드가 th2의 작업을 끝날 때까지 기다린다.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 만약 join을 사용하지 않았다면 main 쓰레드가 먼저 종료되어 시간 출력을 먼저할 수도 있다.
        System.out.println("소요 시간 : " + (System.currentTimeMillis() - startTime));
    }
}

class ThreadEx19_1 extends Thread{
    public void run(){
        for(int i = 0; i < 300; i++){
            System.out.print("-");
        }
    }
}

class ThreadEx19_2 extends Thread{
    public void run(){
        for(int i = 0; i < 300; i++){
            System.out.print("|");
        }
    }
}