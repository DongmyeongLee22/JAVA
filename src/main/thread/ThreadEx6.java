package main.thread;
/* 멀티가 좋은 이유

두 쓰레드가 서로 다른 작원을 사용하는 작업의 경우에는 싱글 쓰레드 프로세스보다 멀티 쓰레드 프로세스가 더 효율적이다.
싱글일 경우 A 사용자의 데이터를 입력받을 때동안 그 쓰레드는 아무것도 하지 않는다. 비 효율적
멀티는 A 사용자의 데이터를 입력받을 때 다른 쓰레드를 실행시킬 수 있게 된다.
 */

import javax.swing.*;

// 싱글 쓰레드
public class ThreadEx6 {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
        System.out.println(input);

        for(int i=0; i<10;i ++){
            System.out.println(i);
            try{
                Thread.sleep(100);
            }
            catch (Exception e){

            }
        }
    }
}

// 멀티 쓰레드
class ThreadEx7{
    public static void main(String[] args) {
        ThreadEx7_1 threadEx7_1 = new ThreadEx7_1();
        threadEx7_1.start();

        String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
        System.out.println(input);

    }


}

class ThreadEx7_1 extends Thread{
    public void run(){
        for (int i = 0 ; i < 10; i++){
            System.out.println(i);
            try{
                Thread.sleep(1000);
            }catch (Exception e){}

        }
    }
}
