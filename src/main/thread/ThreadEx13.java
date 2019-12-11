package main.thread;
/* interrupt(), interrupted() - 쓰레드의 작업 취소
진행 중인 쓰레드의 작업이 끝나기 전에 취소시켜야 할때가 있다. 예를 들어 큰 파일을 다운로드받을 때 시간이 너무 오래 걸리면 중간에
다운로드를 포기하고 취소해야한다. 그럴때 interrupt()를 이용할 수 있다.

단지 쓰레드를 멈추라고하는거지 종료는 불가한다. 즉 쓰레드를 interrupted(인스턴스 변수) 상태로 변경시키는것임
 */

import javax.swing.*;

/*
sleep(), wait(), join()에 의해 '일시정지 상태'에 있을 때, 해당 쓰레드에 대해 interrupt()를 호출하면 Interrupted Exception이 발생하고
쓰레드는 실행대기상태(RUNNABLE)가 된다. 즉 멈춰 있던 쓰레드를 깨운 것이다.
 */

// 사용자가 값을 입력하면 interrupt를 통해 카운트 종료한다.
public class ThreadEx13 {
    public static void main(String[] args) {
        ThreadEx13_1 th1 = new ThreadEx13_1();
        th1.start();

        String input = JOptionPane.showInputDialog("값 입력");
        System.out.println("입력 값 : " + input);
        th1.interrupt();
        System.out.println("th1.isInterrupted() = " + th1.isInterrupted());
    }
}

class ThreadEx13_1 extends Thread{
    public void run(){
        int i = 010;

        while(i!=0 && !isInterrupted()){
            System.out.println(i--);
            for(long x=0; x<25000000000L; x++);
        }

        System.out.println("카운트가 종료되었습니다.");
    }
}
