package concurrency;

public class ExaRunnable implements Runnable{
	private int num = 3;
	private static int count = 0;
	private final int id = count++;
	public ExaRunnable() {
		System.out.println("#" + id + " starting!\n");
	}
	
	private String Print0() {
		return "#" + id + " is running";
	}
	public void run() {
		while (num-- > 0) {
			System.out.println(Print0());
			Thread.yield();
			if (num == 0) {
				System.out.printf("#" + id + " Stoping\n");
			}
		}
	}
}
