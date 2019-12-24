package main.thread;

/* wait, notify 사용 했을 때의 예제
 기능이 잘 되나 wating pool에 CUST와 COOK이 들어 갔을 경우 notify시 누가 통지 받을지 모른다.
 norifyALl해도 랜덤이다.
 운 안좋으면 COOK이 오랬동안 통지 받지 못하는데 이 현상을 *기아 현상*이라고 한다.
 그리고 여러 쓰레드가 lock을 얻기위해 경쟁하는 것을 *경쟁 상태*라고 한다.
 --> 이 문제들은 주석
 --> 해결법 Condition사용 (각각의 waiting pool 만듬)
 */

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * public class ThreadWaitEx3 {
 * public static void main(String[] args) throws InterruptedException {
 * Table table = new Table();
 * <p>
 * new Thread(new Cook(table), "COOK1").start();
 * new Thread(new Customer(table, "donut"), "CUST1").start();
 * new Thread(new Customer(table, "burger"), "CUST2").start();
 * <p>
 * Thread.sleep(5000);
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
 * table.remove(food);
 * System.out.println(name + " ate a " + food);
 * }
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
 * Thread.sleep(100);
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
 * private ReentrantLock lock = new ReentrantLock();
 * private Condition forCook = lock.newCondition();
 * private Condition forCust = lock.newCondition();
 * <p>
 * //동기화
 * public synchronized void add(String dish) {
 * if (dishes.size() >= MAX_FOOD) return;
 * <p>
 * dishes.add(dish);
 * System.out.println("Dishes:" + dishes.toString());
 * }
 * <p>
 * public void remove(String dishName) {
 * synchronized (this) {
 * String name = Thread.currentThread().getName();
 * <p>
 * while (dishes.size() == 0) {
 * System.out.println(name + " is wating.");
 * try {
 * // wait();
 * Thread.sleep(500);
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * }
 * }
 * <p>
 * while (true) {
 * for (int i = 0; i < dishes.size(); i++) {
 * if (dishName.equals(dishes.get(i))) {
 * dishes.remove(i);
 * notify();
 * return;
 * }
 * }
 * <p>
 * try {
 * System.out.println(name + " is wating.");
 * wait();
 * Thread.sleep(500);
 * } catch (InterruptedException e) {
 * }
 * ;
 * }
 * }
 * }
 * <p>
 * public int dishNum() {
 * return dishNames.length;
 * }
 * }
 **/