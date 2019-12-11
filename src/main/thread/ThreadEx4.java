package main.thread;
/*
싱글 코어에서 싱글 쓰레드와 멀티 쓰레드의 작업속도를 비교하면 거의 비슷
오히려 멀티가 느리다. 이유는 작업 전환에 시간이 소모됨.
싱글 : 1 1 1 1 1 2 2 2 2 2
멀티 : 1 2 1 2 1 2 1 2 1 2

그러므로 단순히 CPU만 사용한다면 싱글 코서는 싱글 쓰레드가 더 효율적이다.

참고: 쓰레드 스위칭보다 프로세스 스위칭이 더 많은 정보를 저장해야 하므로 더 많은 시간이 소모됨.
 */

/*
여러번 비교해보면 멀티가 조금 더 늦는것을 알 수 있다.
그 이유는 위에 언급한 작업전환시간과 또 한 쓰레드가 회면에 출력하고 있는 동안 다른 쓰레드는 출력이 끝나기를 기다려야하는 대기시간 때문이다.
 */

/*
병행(concurrent): 여러 쓰레드가 여러 작업을 동시에 진행하는 것
병렬(parallel): 하나의 작업을 여러 쓰레드가 나눠서 처리하는 것
 */

/*
쓰레드 순서가 다른이유는 OS의 프로세스 스케줄러의 영향을 받기 떄문이다.
JVM의 쓰레드 스케줄러에 의해서 어떤 쓰레드가 얼마동안 실행될 것인지 결정하는 것과 같이 프로세스도
프로세스 스케줄러에게 할당되는 실행시간이 일정하지 않고 쓰레드에게 할당되는 시간 역시 일정하지 않게 된다.

자바가 OS 독립적이라고 하나 OS종속적인 부분이 몇가지 있는데 쓰레드가 그 중 하나이다.
 */
//싱글 코어 테스트
public class ThreadEx4 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 300; i++){
            System.out.printf("%s", "-");
            if(i %100 == 0){
                System.out.println();
            }
        }

        System.out.println("싱글 소요시간1 :" + (System.currentTimeMillis()- startTime));

        for (int i = 0; i < 300; i++){
            System.out.printf("%s", "|");
            if(i %100 == 0){
                System.out.println();
            }
        }

        System.out.println("싱글 소요시간2 :" + (System.currentTimeMillis()- startTime));

    }

}

//멀티 코어 테스트
class ThreadEx5{
    static long startTime = 0;

    public static void main(String[] args) {
        ThreadEx5_1 threadEx5_1 = new ThreadEx5_1();
        threadEx5_1.start();
        startTime = System.currentTimeMillis();

        for(int i=0; i < 300; i++){
            System.out.printf("%s", "-");
            if(i %100 == 0){
                System.out.println();
            }
        }

        System.out.println("멀티 소요시간1:" + (System.currentTimeMillis() - ThreadEx5.startTime));
    }
}

class ThreadEx5_1 extends Thread{
    @Override
    public void run() {
        for(int i=0;i<300;i++){
            System.out.printf("%s", "|");
            if(i %100 == 0){
                System.out.println();
            }
        }

        System.out.println("멀티 소요시간2: " + (System.currentTimeMillis() - ThreadEx5.startTime));

    }
}
