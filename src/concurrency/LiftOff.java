package concurrency;

import java.util.concurrent.TimeUnit;

public class LiftOff implements Runnable{
	private static int taskcount = 0;
	private final int id = taskcount++;
	protected int countDown = 10;
	public LiftOff() {
		
	}
	public LiftOff(int countDown) {
		this.countDown = countDown;
	}
	
	public String status() {
		return "#" + id + "(" + (countDown > 0? countDown : "LiftOff!") + "). ";
	}
	public void run() {
		try {
			while (countDown-- > 0) {
				System.out.print(status());
				/*Thread.yield();*/
				TimeUnit.MILLISECONDS.sleep(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		finally {
			System.out.println("Are you alive?");
		}
	}
}
