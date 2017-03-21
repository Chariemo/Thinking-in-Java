package concurrency;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class CheckOutTask<T> implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private Pool<T> pool;
	public CheckOutTask(Pool<T> pool) {
		this.pool = pool;
	}
	
	public void run() {
		try {
			T item = pool.checkOut();
			System.out.println(this + " Checked out " + item);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(item + " checking in");
			pool.checkIn(item);	
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	public String toString() {
		return "CheckOuTask " + id + " ";
	}
}

public class SemaphoreDemo {
	final static int size = 25;
	public static void main(String[] args) throws Exception {
		final Pool<Fat> pool = new Pool<Fat>(Fat.class, size);
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0 ; i < size; i++) {
			executorService.execute(new CheckOutTask<Fat>(pool));
		}
		System.out.println("All CheckOutTask created");
		ArrayList<Fat> list = new ArrayList<Fat>();
		for (int i = 0; i < size; i++) {
			Fat fat = pool.checkOut();
			fat.operation();
			System.out.println(i + ": main() thread checked out");
			list.add(fat);
		}
		Future<?> future = executorService.submit(new Runnable() {
			public void run() {
				try {
					pool.checkOut();
				} catch(InterruptedException exception) {
					System.out.println("CheckOut() Interrupted");
				}
			}
		});
		TimeUnit.SECONDS.sleep(2);
		future.cancel(true);
		System.out.println("Checking in objects in " + list);
		for (Fat fat : list) {
			pool.checkIn(fat);
		}
		for (Fat fat : list) {
			pool.checkIn(fat);
		}
		executorService.shutdown();
	}
}
