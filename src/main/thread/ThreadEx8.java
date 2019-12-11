package main.thread;

/* 쓰레드 우선순위
우선순위의 범위는 1~10이며 숫자가 높을수록 우선순위가 높다.
main 메서드 내에서 생성하는 쓰레드의 우선순위는 기본으로 5이다.

OS의 스케줄러에 종속적이라 예측만 가능하지 확실하지 않다.
차라리 우선순위 큐에 저장해서 우선순위 높은 순으로 하는게 더 좋다고 생각한다 - 자바의 정석 남궁성 -
 */
public class ThreadEx8 {
    public static void main(String[] args) {
    ThreadEx8_1 threadEx8_1 = new ThreadEx8_1();
    ThreadEx8_2 threadEx8_2 = new ThreadEx8_2();

    threadEx8_2.setPriority(10 );

        System.out.println("Priority of th(-) : " + threadEx8_1.getPriority());
        System.out.println("Priority of th(|) : " + threadEx8_2.getPriority());
        threadEx8_1.start();
        threadEx8_2.start();
    }
}

class ThreadEx8_1 extends Thread{
    public void run(){
        for(int i = 0; i < 300; i++){
            System.out.print("-");
            for(int x = 0; x < 10000000; x++);
        }
    }
}

class ThreadEx8_2 extends Thread{
    public void run(){
        for(int i = 0; i < 300; i++){
            System.out.print("|");
            for(int x = 0; x < 10000000; x++);
        }
    }
}
