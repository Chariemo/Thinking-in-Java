package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class LocateCount {
	private long value;
	public LocateCount() {
		this.value = 0;
	}
	
	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public synchronized void Increment() {
		this.value++;
		Thread.yield();
	}
}

class Calculate implements Runnable {
	static long count = 500000;
	private LocateCount locateCount;
	private double x, y;
	public Calculate(LocateCount locateCount) {
		this.locateCount = locateCount;
	}
	public void run() {
		for (int i = 0; i < count; i++) {
			x = Math.random();
			y = Math.random();
			if (x*x + y*y <= 1) { 
				locateCount.Increment();
			}
		}
	}
}

public class CalculatePI {
	public static void main(String[] args) throws InterruptedException {
		double PI;
		int numOfThread = 5;
		ExecutorService executorService = Executors.newCachedThreadPool();
		LocateCount locateCount = new LocateCount();
		for (int i = 0; i < numOfThread; i++) {
			executorService.execute(new Calculate(locateCount));
		}
		executorService.shutdown();
		TimeUnit.SECONDS.sleep(5);		
		PI = (double) 4 * locateCount.getValue() / (numOfThread * Calculate.count);
		System.out.println("PI is " + PI);
	}
}
