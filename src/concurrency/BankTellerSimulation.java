package concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

class Customer {
	private static int counter = 0;
	private final int id= counter++;
	private final int serviceTime;
	public Customer(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	public int getServiceTime() {
		return this.serviceTime;
	}
	public String toString() {
		return "Customer " + id + ":[" + serviceTime + "] ";
	}
}

class CustomerLine extends ArrayBlockingQueue<Customer> {
	public CustomerLine(int maxLineSize) {
		super(maxLineSize);
	}
	
	public String toString() {
		if (this.size() == 0) {
			return "[Empty]";
		}
		StringBuilder result = new StringBuilder();
		for (Customer customer : this) {
			result.append(customer);
		}
		return result.toString();
	}
}

class CustomerGenerator implements Runnable {
	private Random random = new Random();
	private CustomerLine customerLine;
	public CustomerGenerator(CustomerLine customerLine) {
		this.customerLine = customerLine;
	}
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
				customerLine.add(new Customer(random.nextInt(1000)));
			}
		} catch(InterruptedException exception) {
			System.out.println("CustomerGenerator Interrupted");
		}
		System.out.println("CustomerGenerator terminating");
	}
}

class Teller implements Runnable, Comparable<Teller> {
	private static int counter = 0;
	private final int id = counter++;
	private int numOfServed = 0;
	private boolean statusServing = true;
	private CustomerLine customerLine;
	public Teller(CustomerLine customerLine) {
		this.customerLine = customerLine;
	}
	@Override
	public int compareTo(Teller o) {
		// TODO 自动生成的方法存根
		return numOfServed > o.numOfServed ? 1 : (numOfServed < o.numOfServed ? -1 : 0); 
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		try {
			while (!Thread.interrupted()) {
				Customer customer = customerLine.take();
				TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
				synchronized (this) {
					numOfServed++;
					while (!statusServing) {
						wait();
					}
				}
			}
		} catch(InterruptedException exception) {
			System.out.println(this + " Interrupted");
		}
		System.out.println(this + " terminating");
	}
	
	public synchronized void doSomethingElse() {
		numOfServed = 0;
		statusServing = false;
	}
	
	public synchronized void serveCustomerLine() {
		assert !statusServing: "already serving: " + this;
		statusServing = true;
		notifyAll();
	}
	
	public String toString() {
		return "Teller " + id + " ";
	}
	public String shortString() {
		return "T" + id;
	}
}

class TellerManager implements Runnable {
	private CustomerLine customerLine;
	private ExecutorService executorService;
	private PriorityBlockingQueue<Teller> tellerQueue = new PriorityBlockingQueue<Teller>();
	private Queue<Teller> doSomethingElser = new LinkedList<Teller>();
	private int adjustmentPeriod;
	
	public TellerManager(ExecutorService executorService, CustomerLine customerLine, int adjustmentPeriod) {
		// TODO 自动生成的构造函数存根
		this.executorService = executorService;
		this.adjustmentPeriod = adjustmentPeriod;
		this.customerLine = customerLine;
		Teller teller = new Teller(customerLine);
		executorService.execute(teller);
		tellerQueue.add(teller);
	}
	
	public void adjustTellerNumber() {
		if (customerLine.size() / tellerQueue.size() > 2) {
		if (doSomethingElser.size() > 0) {
				Teller teller = doSomethingElser.remove();
				teller.serveCustomerLine();
				tellerQueue.offer(teller);
				return;	
			}
			Teller teller = new Teller(customerLine);
			executorService.execute(teller);
			tellerQueue.add(teller);
			return;
		}
		if (tellerQueue.size() > 1 && customerLine.size() / tellerQueue.size() < 2) {
			reassignOneTeller();
		}
		if (customerLine.size() == 0) {
			while (tellerQueue.size() > 1) {
				reassignOneTeller();
			}
		}
	}
	
	private void reassignOneTeller() {
		Teller teller = tellerQueue.poll();
		teller.doSomethingElse();
		doSomethingElser.add(teller);
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
				adjustTellerNumber();
				System.out.println(customerLine + "{ ");
				for (Teller teller : tellerQueue) {
					System.out.println(teller.shortString() + " ");
				}
				System.out.println("}");
			}
		} catch(InterruptedException exception) {
			System.out.println(this + "Interrupted");
		}
		System.out.println(this + "terminating");
	}
	public String toString() {
		return "TellerManager ";
	}
	
}
public class BankTellerSimulation {
	static final int MAX_LINE_SIZE = 50;
	static final int ADJUSTMENT_PERIOD = 1000;
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		CustomerLine customerLine = new CustomerLine(MAX_LINE_SIZE);	
		executorService.execute(new CustomerGenerator(customerLine));
		executorService.execute(new TellerManager(executorService, customerLine, ADJUSTMENT_PERIOD));
		TimeUnit.SECONDS.sleep(5);
		executorService.shutdownNow();
	}
}