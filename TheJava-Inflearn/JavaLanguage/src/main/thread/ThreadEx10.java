package main.thread;
/* 데몬 쓰레드
 ==> 다른 일반 쓰레드(데몬이 아닌)의 작업을 돕는 보조적인 역할을 하는 쓰레드
일반 쓰레드가 모두 종료되면 데몬 쓰레드는 강제적으로 자동 종료. (보조 역할이므로 일반이 없으면 존재의 의미가 없음)
ex) 가비지 컬렉터, 워드프로세서 자동저장, 화면 자동 갱신

무한 루프와 조건물을 이용해 실행 후 대기하다가 특정 조건 만족시 작업을 수행하고 다시 대기하도록 작성한다.
데몬쓰레드가 생성한 쓰레드는 자동적으로 데몬쓰레드가 된다.
 */

/**
 * 3초마다 변수 autoSave를 확인하여 true면 autoSave()를 호출하는 일을 무한히 반복하는 쓰레드 만들기
 * 이 쓰레드를 데몬 쓰레드로 만들지 않아다면 프로그램을 종료하지 않는 한 영원히 실행
 * 데몬쓰레드는 strat() 전에 실행되어야 한다. 아니면 예외 발생
 */
public class ThreadEx10 implements Runnable {

    static boolean autoSave = false;

    public static void main(String[] args) {
        Thread t= new Thread(new ThreadEx10());
        t.setDaemon(true);
        t.start();

        for(int i = 1; i <= 10; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(i);

            if (i==5){
                autoSave = true;
            }
        }

        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (autoSave){
                autoSave();
            }
        }
    }

    public void autoSave(){
        System.out.println("작업파일이 자동저장되었습니다.");
    }
}
