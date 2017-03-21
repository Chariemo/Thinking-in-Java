package concurrency;

import java.awt.List;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.activation.DataContentHandler;

public class ActiveObjectDemo {
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	private Random random = new Random();
	private void pause(int factor) {
		try {
			TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(factor));
		} catch(InterruptedException exceptioin) {
			System.out.println("Sleep() Interrupted");
		}
	}

	public Future<Integer> calculateInt(final int x, final int y) {
		return executorService.submit(new Callable<Integer>() {
			public Integer call() {
				System.out.println("staring " + x + " + " + y);
				pause(500);
				return x + y;
			}
		});
		
	}
	public Future<Float> calculateFloat(final float x, final float y) {
		return executorService.submit(new Callable<Float>() {
			public Float call() {
				System.out.println("starting " + x + " + " + y);
				pause(2000);
				return x + y;
			}
		});
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
	
	public static void main(String[] args) {
		ActiveObjectDemo d1 = new ActiveObjectDemo();
		CopyOnWriteArrayList<Future<?>> results = new CopyOnWriteArrayList<Future<?>>();
		for (float f = 0.0f; f < 1.0f; f += 0.2f) {
			results.add(d1.calculateFloat(f, f));
		}
		
		for (int i = 0; i < 5; i++) {
			results.add(d1.calculateInt(i, i));
		}
		System.out.println("All asynch calls made");
		while(results.size() > 0) {
			for (Future<?> future : results) {
				if (future.isDone()) {
					try {
						System.out.println(future.get());
					} catch(Exception exception) {
						throw new RuntimeException(exception);
					}
					results.remove(future);
				}
			}
		}
		d1.shutdown();
		
	}
}
