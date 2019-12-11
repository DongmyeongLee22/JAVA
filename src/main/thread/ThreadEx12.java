package main.thread;

/*
쓰레드 프로그래밍이 어려운 이유는 동기화와 스케줄링 때문이다.
효율적인 멀티쓰레드 프로그램을 만들기 위해서는 보다 정교한 스케줄링을 통해 프로세스에게 주어진 자원과 시간을
여러쓰레드가 낭비없으 잘 사용하도록 프로그램이 해야 한다.
 */

/*쓰레드 상태
NEW : 쓰레드가 생성되고 아직 start() 가 호출되지 않은 상태
RUNNABLE : 실행 중 또는 실행 가능한 상태
BLOCK : 동기화블럭에 의해서 일시정지된 상태(lock이 풀릴 때까지 기다리는 상태)
WAITING, TIMED_WAITING : 쓰레드의 작업이 종료되지는 않았지만 실행가능하지 않은 일시정지 상태, TIMED_WAITNG은 일시정지시간이 지정된 경우
TERMINATED : 쓰레드의 작업이 종료된 상태
 */
public class ThreadEx12 {

    public static void main(String[] args) {
        ThreadEx12_1 th1 = new ThreadEx12_1();
        ThreadEx12_2 th2 = new ThreadEx12_2();

        th1.start();
        th2.start();

        try {
            //이렇게 하고 실행해도 th1이 항상 늦게 종료되지 않는다
            //그 이유는 sleep은 항상 현재 실행 중인 쓰레드에 영향을 받기떄문에 main에 적용된다.
            th1.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("<<메인 종료>>");
    }
}

class ThreadEx12_1 extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("-");
        }

        System.out.print("<<th1 종료>>");
    }
}

class ThreadEx12_2 extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("|");
        }
        System.out.print("<<th2 종료>>");
    }
}