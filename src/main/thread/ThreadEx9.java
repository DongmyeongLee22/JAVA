package main.thread;

/* 쓰레드 그룹
쓰레드 그룹은 보안상의 이유로 도입된 개념
자신의 속한 쓰레드 그룹이나 하위 쓰레드 그룹은 변경할 수 있지만 다른 쓰레드 그룹의 쓰레드를 변경할 수 없다.

모든 쓰레드는 반드시 그룹에 들어가야한다. 그러므로 따로 지정하지 않으면 기본적으로 자신을 생성한 쓰레드와 같은 그룹에 속하게 된다.

자바 앱이 실행되면 JVM은 main과 system이라는 쓰레드 그룹을 만들고 JVM 운영에 필요한 쓰레드들을 생성해서 이 쓰레드 그룹에 포함시킨다.
예를 들어 main 메서드를 수행하는 main이라는 이름의 쓰레드는 main 쓰레드 그룹에 속하고, 가비지 컬렉션을 구행하는 Finalizer 쓰레드는
system 쓰레드 그룹에 속한다.
우리가 생성하는 모든 쓰레드 그룹은 main쓰레드 그룹의 하위 쓰레드 그룹이 되며 쓰레드 그룹을 지정하지 않고 생성한 쓰레드는 자동적으로
main 쓰레드 그룹에 속하게 된다.
 */
public class ThreadEx9 {
    public static void main(String[] args) {
        ThreadGroup main = Thread.currentThread().getThreadGroup();
        ThreadGroup group1 = new ThreadGroup("Group1");
        ThreadGroup group2 = new ThreadGroup("Group2");

        ThreadGroup subGroup1 = new ThreadGroup(group1, "SubGroup1");

        group1.setMaxPriority(3);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //참조변수 없이 쓰레드를 생성해서 실행해도 가비지 컬렉터의 제거 대상이 되지 않음
        //이 쓰레드 참조가 ThreadGroup에 저장되어 있기 떄문이다.
        new Thread(group1, runnable, "th1").start();
        new Thread(subGroup1, runnable, "th2").start();
        new Thread(group2, runnable, "th3").start();

        System.out.println("List of ThreadGroup : " + main.getName());
        System.out.println("Active ThreadGroup: " + main.activeGroupCount());
        System.out.println("Active Thread: " + main.activeCount());


        main.list();
        /*
        타임을 준 이유는 정보 출력전에 쓰레드 종료될까봐

        실행해보면 모두 main 쓰레드에 속하는것을 확인 할 수 있다.
        그리고 우선순위가 그 전에 설정되고 우선순위 설정된 그룹의 하위 쓰레드는 모두 그 우선순위로 적용된다.
         */
    }
}
