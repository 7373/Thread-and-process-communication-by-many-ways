package threadMessage.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import threadMessage.common.test.ThreadToGo;

public class test {

	private final ThreadToGo threadToGo = new ThreadToGo();
	private static final ExecutorService tPool = Executors.newFixedThreadPool(2);

	public static String[] buildNoArr(int max) {
		String[] noArr = new String[max];
		for (int i = 0; i < max; i++) {
			noArr[i] = Integer.toString(i + 1);
		}
		return noArr;
	}

	public static String[] buildCharArr(int max) {
		String[] charArr = new String[max];
		int tmp = 65;
		for (int i = 0; i < max; i++) {
			charArr[i] = String.valueOf((char) (tmp + i));
		}
		return charArr;
	}

	public static void print(String... input) {
		if (input == null)
			return;
		for (String each : input) {
			System.out.print(each);
		}
	}

	public void run(Runnable r) {
		tPool.submit(r);
	}

	public void shutdown() {
		tPool.shutdown();
	}

	public Runnable newThreadOne() {
		final String[] inputArr = this.buildNoArr(52);
		return new Runnable() {
			private String[] arr = inputArr;

			public void run() {
				try {
					for (int i = 0; i < arr.length; i = i + 2) {
						synchronized (threadToGo) {
							while (threadToGo.value == 2){
								System.out.println("1111111");
								threadToGo.wait();
							}
//							this.print(arr[i], arr[i + 1]);
							System.out.println(arr[i]+arr[i + 1]);
							threadToGo.value = 2;
							threadToGo.notify();
							System.out.println("线程1已释放锁");
						}
					}
				} catch (InterruptedException e) {
					System.out.println("Oops...");
				}
			}
		};
	}

	public Runnable newThreadTwo() {
		final String[] inputArr = this.buildCharArr(26);
		return new Runnable() {
			private String[] arr = inputArr;

			public void run() {
				try {
					for (int i = 0; i < arr.length; i++) {
						synchronized (threadToGo) {
							while (threadToGo.value == 1){
								System.out.println("2222");
								threadToGo.wait();
								
							}
//							this.print(arr[i]);
							System.out.println(arr[i]);
							threadToGo.value = 1;
							threadToGo.notify();
							System.out.println("线程2已释放锁");
						}
					}
				} catch (InterruptedException e) {
					System.out.println("Oops...");
				}
			}
		};
	}

	class ThreadToGo {
		int value = 1;
	}

	public static void main(String args[]) throws InterruptedException {
		test one = new test();
		one.run(one.newThreadOne());
		one.run(one.newThreadTwo());
	
		
		one.shutdown();
	}
}
