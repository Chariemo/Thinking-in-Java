package concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class EvenGenerator extends IntGenerator{
	private int currentEvenValue = 0;
	/*public synchronized int next() {
		++currentEvenValue;
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}*/
	
	private ReentrantLock lock = new ReentrantLock();
	public int next(){
		boolean captured = false;
		try {
			captured = lock.tryLock(2, TimeUnit.SECONDS);
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;
			return currentEvenValue;
			
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			throw new RuntimeException(e);
		} finally {
			if (captured) {
				lock.unlock();
			}
		} 
	}
	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator());
	}
	
	
	
}
