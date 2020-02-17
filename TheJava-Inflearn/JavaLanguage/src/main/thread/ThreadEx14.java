package main.thread;

import javax.swing.*;
/*
sleep으로 시간차를 주면 카운트가 종료되지 않는다.
그 이유는 Thread.sleep시 InterruptedException이 발생하여 interrupted 상태를 자동으로 false로 초기화한다.
 */
public class ThreadEx14 {
    public static void main(String[] args) {
        ThreadEx14_1 th1 = new ThreadEx14_1();
        th1.start();

        String input = JOptionPane.showInputDialog("값 입력");
        System.out.println("입력 값 : " + input);
        th1.interrupt();
        System.out.println("th1.isInterrupted() = " + th1.isInterrupted());
    }
}

class ThreadEx14_1 extends Thread{
    public void run(){
        int i = 10;

        while(i!=0 && !isInterrupted()){
            System.out.println(i--);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("카운트가 종료되었습니다.");
    }
}
