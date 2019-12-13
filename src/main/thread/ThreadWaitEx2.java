package main.thread;

import java.util.ArrayList;
/* 동기화 했을 때의 예제

 동기화해도 계속 CUST1이 waiting중이다. 왜냐하면 CUST1이 lock을 쥐고 있기 때문에
 */

/**
 * public class ThreadWaitEx2 {
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
 * //동기화
 * public synchronized void add(String dish) {
 * if (dishes.size() >= MAX_FOOD) return;
 * <p>
 * dishes.add(dish);
 * System.out.println("Dishes:" + dishes.toString());
 * }
 * <p>
 * public boolean remove(String dishName) {
 * synchronized (this){
 * while (dishes.size() == 0){
 * String name = Thread.currentThread().getName();
 * System.out.println(name + " is wating.");
 * try {
 * Thread.sleep(500);
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * }
 * }
 * <p>
 * for (int i = 0; i < dishes.size(); i++) {
 * if (dishName.equals(dishes.get(i))) {
 * dishes.remove(i);
 * return true;
 * }
 * }
 * <p>
 * return false;
 * }
 * }
 * <p>
 * public int dishNum() {
 * return dishNames.length;
 * }
 * }
 **/