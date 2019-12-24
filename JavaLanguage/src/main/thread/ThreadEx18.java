package main.thread;

/*
쓰레드 yield() 이용
yield : 다른 쓰레드에게 양보한다.
예를들어 스케줄러에 의해 1초의 실행시간을 할당받은 쓰레드가  0.5초 작업하고 yield가 호출되면 나머지0.5초는 포기하고
다시 실행대기상태가(RUNNABLE) 된다.
 */
public class ThreadEx18 {
    public static void main(String[] args) {

        RunImplEx18 th1 = new RunImplEx18("*");
        RunImplEx18 th2 = new RunImplEx18("**");
        RunImplEx18 th3 = new RunImplEx18("***");

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

class RunImplEx18 implements Runnable {

    volatile private boolean suspended = false;
    volatile private boolean stopped = false;

    Thread th;

    public RunImplEx18(String name) {
        this.th = new Thread(this, name);
    }

    @Override
    public void run() {

        String name = th.getName();

        /*
        Ex17은 stopped = false이고 susfpeded가 true이면 의미없이 while문을 돈다
        이를 바쁜 대기상태(busy-waiting) 이라고 한다.
        Ex18은 yield()를 통해 while문을 낭비하지 않고 다른 쓰레드에게 양보하게 되므로 더 효율적이다.
         */
        while (!stopped) {
            if (!suspended) {
                System.out.println(name);
                try {
                    System.out.println("thread.sleep");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(name + " - interrupted");
                }
            }else{
                Thread.yield();
            }
        }


    }


    /*
    interrupt를 사용하지 않으면 값을 true로 바꿔도 sleep에 의해 최대 지연시간이 1초 생길수도 있다.
    그러므로 interrupt를 이용하면 sleep 호출시 예외를 던지므로 응답성이 좋아진다.
     */
    public void suspend() {
        suspended = true;
        th.interrupt();
        System.out.println(th.getName() + " - interrupt() by suspend()");
    }

    public void stop() {
        stopped = true;
        th.interrupt();
        System.out.println(th.getName() + " - interrupt() by stop()");
    }

    public void resume() {
        suspended = false;

    }

    public void start(){
        th.start();
    }
}
