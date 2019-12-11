package main.thread;

/*
교착 상태(膠着狀態, 영어: deadlock)란 두 개 이상의 작업이 서로 상대방의 작업이 끝나기 만을 기다리고 있기 때문에
결과적으로 아무것도 완료되지 못하는 상태
 */
/*
suspend, resume, stop은 교착상태를 일으킬수 있으므로 Deprecated 되었음.

Deprecated: 전에는 사용했지만 앞으로는 사용권장하지 않는 것들..
 */

/*
위 3개의 문제를 해결하는 방법
boolean쪽에 volatile을 붙여줘야 한다.
 */
public class ThreadEx16 {
    public static void main(String[] args) {


        RunImplEx16 r1 = new RunImplEx16();
        RunImplEx16 r2 = new RunImplEx16();
        RunImplEx16 r3 = new RunImplEx16();
        Thread th1 = new Thread(r1, "*");
        Thread th2 = new Thread(r2, "**");
        Thread th3 = new Thread(r3, "***");

        th1.start();
        th2.start();
        th3.start();
        try{
            Thread.sleep(2000);
            r1.suspend();
            Thread.sleep(2000);
            r2.suspend();
            Thread.sleep(3000);
            r1.resume();
            Thread.sleep(3000);
            r1.stop();
            r2.stop();
            Thread.sleep(2000);
            r3.stop();
        }catch (Exception e){

        }
    }
}

class RunImplEx16 implements Runnable {

    volatile private boolean suspended = false;
    volatile private boolean stopped = false;


    @Override
    public void run() {

        while (!stopped) {
            if (!suspended) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " - stopped");

    }

    public void suspend() {
        suspended = true;
    }

    public void resume() {
        suspended = false;
    }

    public void stop() {
        stopped = true;
    }
}
