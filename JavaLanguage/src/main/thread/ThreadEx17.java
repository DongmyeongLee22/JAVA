package main.thread;

/*
Ex16 리팩토링
 */
public class ThreadEx17 {
    public static void main(String[] args) {

        RunImplEx17 th1 = new RunImplEx17("*");
        RunImplEx17 th2 = new RunImplEx17("**");
        RunImplEx17 th3 = new RunImplEx17("***");

        th1.start();
        th2.start();
        th3.start();

        try{
            Thread.sleep(2000);
            th1.suspend();
            Thread.sleep(2000);
            th2.suspend();
            Thread.sleep(3000);
            th1.resume();
            Thread.sleep(3000);
            th1.stop();
            th2.stop();
            Thread.sleep(2000);
            th3.stop();
        }catch (Exception e){

        }
    }
}

class RunImplEx17 implements Runnable {

    volatile private boolean suspended = false;
    volatile private boolean stopped = false;

    Thread th;

    public RunImplEx17(String name) {
        this.th = new Thread(this, name);
    }

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

    public void start(){
        th.start();
    }
}
