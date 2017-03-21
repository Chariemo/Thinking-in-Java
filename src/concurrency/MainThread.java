package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainThread {
	public static void main(String[] args) {
		/*for (int i = 0; i < 5; ++i) {
			new Thread(new ExaRunnable()).start();
		}
		System.out.println("Waiting for ExaRunnable()\n");*/
		
		
		
		/*ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();*/
		
		
		
		/*ExecutorService exc = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 5; i++) {
			exc.execute(new LiftOff());
		}
		exc.shutdown();*/
		
		
		ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 5; i++) {
			exec.execute(new LiftOff());
		}
		System.out.println("All Daemons started");
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
}
