package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
	private IntGenerator generator = null;
	private final int id;
	public EvenChecker(IntGenerator g, int ident) {
		this.generator = g;    
		this.id = ident;
	}
	public void run() {
		while (!generator.isCanceled()) {   }
	}
	
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < count; ++i) {
			executorService.execute(new EvenChecker(gp, count));
		}
		executorService.shutdown();
	}
	
	public static void test(IntGenerator gp) {
		test(gp, 10);
	}
}
