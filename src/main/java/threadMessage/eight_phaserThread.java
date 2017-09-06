package threadMessage;

import java.util.concurrent.Phaser;

public class eight_phaserThread {
	final static String[] inputInt = Helper.buildNoArr(52);
	final static String[] inputChar = Helper.buildCharArr(26);
	public static void main(String[] args) {

		Phaser phaser = new Phaser(1);
		Task_01 task_01 = new Task_01(phaser);
		Thread thread = new Thread(task_01);
		thread.start();

	}

	static class Task_01 implements Runnable {
		private final Phaser phaser;

		public Task_01(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			for (int count = 0; count < 26; count++) {
				//System.out.println(count+Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
				System.out.print(inputInt[2*count]+inputInt[2*count+1]);
				// 等待其他任务执行完成
				phaser.arriveAndAwaitAdvance();
				//System.out.println(count+Thread.currentThread().getName() + "继续执行任务...");
				System.out.print(inputChar[count]);
			}
		}
	}
}