package main.thread;
/*
join의 사용 실제 예제
 */

/*
결론은 우선순위를 ㅇ낮춰서 순서를 정하기 보다는 sleep을 이용해서 주기적으로 실행되도록 하다가
필요할때 마다 interrupt를 호출해서 즉시 가비지 컬렉션이 이루어지도록 하는게 좋으며 필요시 join을 사용한다.
 */
public class ThreadEx20 {
    public static void main(String[] args) {
        ThreadEx20_1 gc = new ThreadEx20_1();
        gc.setDaemon(true);
        gc.start();

        int requiredMemory = 0;

        for(int i = 0; i < 20; i++){
            requiredMemory = (int) (Math.random() * 10) * 20;

            // 필요한 메모리가 사용할 수 있는 양보다 크거나 전체 메모리의 60% 이상을 사용할 경우 gc를 깨운ㄷ
            if (gc.freeMemory() < requiredMemory || gc.freeMemory() < gc.totalMemory() * 0.4){
                gc.interrupt();

                // join을 사용하지 않으면 메모리가 1000이 넘어도 계속증가한다,
                // interrupt를 보내도 그 interrupt가 수행되기 전에 main쓰레드에서 작업이 수행되기 떄문에...
                try {
                    gc.join(100); // 0.1동안 gc 실행
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            gc.usedMemory += requiredMemory;
            System.out.println("gc.usedMemory = " + gc.usedMemory);
        }
    }
}

class ThreadEx20_1 extends Thread{
    final static int MAX_MEMORY = 100;
    int usedMemory = 0;

    public void run(){
        while (true){
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gc();
            System.out.println("Garbage Collected. Free Memory : " + freeMemory());
        }
    }

    public void gc() {
        usedMemory -= 300;
        if (usedMemory < 0) usedMemory = 0;
    }

    public int totalMemory(){
        return MAX_MEMORY;
    }

    public int freeMemory() {
        return MAX_MEMORY - usedMemory;
    }
}
