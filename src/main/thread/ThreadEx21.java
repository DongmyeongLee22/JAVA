package main.thread;

/* 쓰레드 동기화
 멀티쓰레드 프로세스의 경우 여러쓰레드가 같은 프로세스 내의 자원을 공유해서 작업하기 때문에 서로의 작업에 영향을 주게된다.
  만일 A 쓰레드가 작업하던 도중에 다른 B쓰레드에게 제어권이 넘어갓을 때, A 쓰레드가 작업하던 공유데이터를 B쓰레드가 임의로 변경하였다면
  문제가 발생할 수도 있다. 이러한 문제를 해결하기 위해 도입된 개념이 *임계영역*, *락*이다.

  공유데이터를 사용하는 코드 영을 *임계영역*으로 지정해놓고, 공유데이터(객체)가 가지고 있는 *lock*을 획득한 단 하나의 쓰레드만 이 영역 내의
  코드를 수행할 수 있게한다. 그리고 해당 쓰레드가 임계 영역 내의 모든 코드를 수행하고 벗어나서 *lock*을 반납해야만 다른 쓰레드가 반납된
  *lock*을 획득하여 임계 영역의 코드를 수행할 수 있게 된다.

  이처럼 한 쓰레드가 진행중인 작업을 다른 쓰레드가 간섭하지 못하도록 막는 것을 *쓰레드의 동기화*라고 한다.
 */
public class ThreadEx21 {

    public static void main(String[] args) {

        /* 실행 시 돈이 음수가 됨.
           -> 한 쓰레드가 if문 조건식을 통과하고 출금하기 바로 직전에 다른 쓰레드가 끼어들어 출금을 먼저했기 떄문이다.
           즉 if문과 출금하는 부분을 *임계 영역*으로 묶어야 한다.
         */
        Runnable runnable = new RunnableEx21();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }

}

class Account {

    /*
    이 값이 private가 아닌 public이면 동기화 의미가 없음. 다른데서 값을 변경할수 있기 때문에
    그러므로 꼭 *private*으로 해야한다.
     */
    private int balance = 1000;

    public int getBalance() {
        return balance;
    }

    /*
    한 쓰레드에서 이 메서드가 호출되면 이 메서드가 종료되어 lock을 반납할 때 까지 다른 쓰레드는 이 메서드를 호출해도 대기상태에 있게 된다.
     */
    public synchronized void withdraw(int moeny) {
        if (balance >= moeny) {
            try {
                Thread.sleep(1000);
                balance -= moeny;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 블럭으로 동기화, 위 방법이 더 나음.
    public void withdrawUsingBlock(int moeny) {
        synchronized (this) {
            if (balance >= moeny) {
                try {
                    Thread.sleep(1000);
                    balance -= moeny;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 동기화 하기 전, 값이 음수가 됨.
//    public void withdraw(int moeny){
//        if (balance >= moeny){
//            try{
//                Thread.sleep(1000);
//                balance -= moeny;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

class RunnableEx21 implements Runnable {

    Account account = new Account();

    @Override
    public void run() {
        while (account.getBalance() > 0) {

            int money = (int) (Math.random() * 3 + 1) * 100;
            account.withdraw(money);
            System.out.println("balance = " + account.getBalance());
        }
    }
}

