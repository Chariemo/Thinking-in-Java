package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionThread implements Runnable {
	public void run() {
		Thread thread = Thread.currentThread();
		System.out.println("Run() by " + thread);
		System.out.println();
	}
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO 自动生成的方法存根
		System.out.println("Caught " + e);
	}
	
}

class HandlerThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		// TODO 自动生成的方法存根
		System.out.println(this + " creating new Thread");
		Thread thread = new Thread();
		System.out.println("created " + thread); 
		thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("eh = " + thread.getUncaughtExceptionHandler());
		return thread;
	}
	
}
public class CaptureUncaughtException {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
		executorService.execute(new ExceptionThread());
	}
}
