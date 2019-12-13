package main.thread;

import java.util.ArrayList;

/* wait(), notify()

 만일 계죄에 출금할 돈이 부족해서 한 쓰레드가 락을 보유한 채로 돈이 입금될때 까지 오랜 시간을 보낸다면, 다른 쓰레드들은 모두 해당 객체의
 락을 기다리느라 작업을 원할히 수행하지 않을 것이다.

 동기화된 임계영역의 코드를 수행하다가 더 이상 진행할 상황이 아니면, 일단 wait()을 호출하여 쓰레드가 락을 반납하고 기다리게 하낟.
 그러면 다른 쓰레드가 락을 얻어 해당 객체에 대한 작업을 수행할 수 있게 된다. 나중에 작업을 다시 진행할 상황이 되면 notify()를 호출
 하여 작업을 중단했던 쓰레드가 다시 락을 얻어 작업을 진행할 수 있게 한다.

 *waiting pool*: wait이 호출되었을때 실행 중이던 쓰레드의 대기실. notifyAll()하면 대기실에 있던 모든 쓰레드에게 통보를 하지만
  그래도 lock을 얻을수 있는것은 한 쓰레드이다.

  wait(), notify(), notifyAll()
  ==> Object에 정의되어 있다.
  ==> 동기화 블록내에서만 사용가능
  ==> 보다 효율적인 동기화를 가능하게 한다.
 */

/* 동기화 안했을 때의 예제

 Cook 쓰레드가 테이블에 음식을 놓는 도중에 Customer가 음식을 가져가려고해서 발생하는 예외
 Customer 쓰레드가 테이블의 마지막 음식을 가져가는 도중에 다른 손님 쓰레드가 먼저 가져가서 발생하는 예외
 */
/**
 * public class ThreadWaitEx1 {
 * public static void main(String[] args) throws InterruptedException {
 * Table table = new Table();
 * <p>
 * new Thread(new Cook(table), "COOK1").start();
 * new Thread(new Customer(table, "donut"), "CUST1").start();
 * new Thread(new Customer(table, "burger"), "CUST2").start();
 * <p>
 * Thread.sleep(100);
 * System.exit(0);
 * }
 * }
 * <p>
 * class Customer implements Runnable {
 * private Table table;
 * private String food;
 * <p>
 * public Customer(Table table, String food) {
 * this.table = table;
 * this.food = food;
 * }
 *
 * @Override public void run() {
 * while (true) {
 * try {
 * Thread.sleep(10);
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * }
 * String name = Thread.currentThread().getName();
 * <p>
 * if (eatFood()) {
 * System.out.println(name + " ate a " + food);
 * } else {
 * System.out.println(name + " failed to eat. :(");
 * }
 * }
 * }
 * <p>
 * boolean eatFood() {
 * return table.remove(food);
 * }
 * }
 * <p>
 * <p>
 * class Cook implements Runnable {
 * private Table table;
 * <p>
 * public Cook(Table table) {
 * this.table = table;
 * }
 * @Override public void run() {
 * while (true) {
 * <p>
 * int idx = (int) (Math.random() * table.dishNum());
 * table.add(table.dishNames[idx]);
 * <p>
 * try {
 * Thread.sleep(1);
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * }
 * <p>
 * }
 * }
 * }
 * <p>
 * class Table {
 * <p>
 * String[] dishNames = {"donut", "donut", "burger"};
 * final int MAX_FOOD = 6;
 * <p>
 * private ArrayList<String> dishes = new ArrayList<>();
 * <p>
 * public void add(String dish) {
 * if (dishes.size() >= MAX_FOOD) return;
 * <p>
 * dishes.add(dish);
 * System.out.println("Dishes:" + dishes.toString());
 * }
 * <p>
 * public boolean remove(String dishName) {
 * for (int i = 0; i < dishes.size(); i++) {
 * if (dishName.equals(dishes.get(i))) {
 * dishes.remove(i);
 * return true;
 * }
 * }
 * <p>
 * return false;
 * }
 * <p>
 * public int dishNum() {
 * return dishNames.length;
 * }
 * }
 **/