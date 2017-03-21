package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import concurrency.Toast.Status;

class Toast {
	public enum Status {DRY, BUTTERED, JAMMED};
	private Status status = Status.DRY;
	private final int id;
	public Toast(int id) {
		this.id = id;
	}
	public void butter() {
		this.status = Status.BUTTERED;
	}
	public void jam() {
		this.status = Status.JAMMED;
	}
	public int getId() {
		return this.id;
	}
	public Status getStatus() {
		return this.status;
	}
	public String toString() {
		return "Toast " + id + ": " + this.status; 
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast> {
}

class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count = 0;
	private Random random = new Random(47);
	public Toaster(ToastQueue toastQueue) {
		this.toastQueue = toastQueue;
	}
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100+random.nextInt(500));
				Toast toast = new Toast(count++);
				System.out.println(toast);
				toastQueue.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Toaster Interrupted");
		}
		System.out.println("Toaster Off");
	}
}

class Buffer implements Runnable {
	private ToastQueue toastDry, toastBuffer;
	public Buffer(ToastQueue toastDry, ToastQueue toastBuffer) {
		this.toastDry = toastDry;
		this.toastBuffer = toastBuffer;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Toast toast = toastDry.take();
				toast.butter();
				System.out.println(toast);
				toastBuffer.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Buffer Interrupted");
		}
		System.out.println("Buffer Off");
	}
}

class Jammer implements Runnable {
	private ToastQueue toastBuffer, toastJammer;
	public Jammer(ToastQueue toastBuffer, ToastQueue toastJammer) {
		this.toastBuffer = toastBuffer;
		this.toastJammer = toastJammer;
	}
	
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = toastBuffer.take();
				toast.jam();
				System.out.println(toast);
				toastJammer.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer Interrupted");
		}
		System.out.println("Jammer Off");
	}
}

class Eater implements Runnable {
	private ToastQueue toastJammer;
	private int counter = 0;
	public Eater(ToastQueue toastJammer) {
		this.toastJammer = toastJammer;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				Toast toast = toastJammer.take();
				if(toast.getId() != counter++ || toast.getStatus() != Status.JAMMED) {
					System.out.println(">>>>>>>>>Error: " + toast);
					System.exit(0);
				}
				else {
					System.out.println("Chomp " + toast);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Eater Interrupted");
		}
		System.out.println("Eater Off");
	}
}
public class ToastOMatic {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ToastQueue	toastDry = new ToastQueue(),
				toastBuffer = new ToastQueue(),
				toastJammer = new ToastQueue();
		executorService.execute(new Toaster(toastDry));
		executorService.execute(new Buffer(toastDry, toastBuffer));
		executorService.execute(new Jammer(toastBuffer, toastJammer));
		executorService.execute(new Eater(toastJammer));
		TimeUnit.SECONDS.sleep(5);
		executorService.shutdownNow();
	}
}
