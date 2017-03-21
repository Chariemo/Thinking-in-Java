package concurrency;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;
	private Random random = new Random(47);
	public TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}
	public void run() {
		try {
			doWork();
			latch.countDown();
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	public void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
		System.out.println(this + " completed");
	}
	public String toString() {
		return String.format("%1$-3d", id);
	}
}


class WaittingTask implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch countDownLatch;
	public WaittingTask(CountDownLatch latch) {
		this.countDownLatch = latch;
	}
	public void run() {
		try {
			countDownLatch.await();
			System.out.println("Latch barrier passed for " + this);
		} catch(InterruptedException exception) {
			System.out.println(this + " interrupted");
		}
	}
	public String toString() {
		return String.format("WaittingTask %1$-3d", id);
	}
}
public class CountDownLatchDemo {
	static final int SIZE = 100;
	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(SIZE);
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			executorService.execute(new WaittingTask(countDownLatch));
		}
		for (int i = 0; i < SIZE; i++) {
			executorService.execute(new TaskPortion(countDownLatch));
		}
		System.out.println("Launched all tasks");
		executorService.shutdown();
	}
}
