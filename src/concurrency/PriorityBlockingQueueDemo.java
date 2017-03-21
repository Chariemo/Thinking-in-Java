package concurrency;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {

	private Random  random = new Random(47);
	private static int counter = 0;
	private final int id = counter++;
	private final int priority;
	protected static ArrayList<PrioritizedTask> List = new ArrayList<PrioritizedTask>();
	public PrioritizedTask(int priority) {
		this.priority = priority;
		List.add(this);
	}
	@Override
	public int compareTo(PrioritizedTask o) {
		// TODO 自动生成的方法存根
		return priority > o.priority ? -1 : (priority < o.priority ? 1 : 0);
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		try {
			TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
		System.out.println(this);
	}
	
	public String toString() {
		return String.format("%1$-3d", priority) + " Task " + id;
	}
	
	public String summary() {
		return "(" + id + ":" + priority + ")";
	}
	
	public static class EndSentnel extends PrioritizedTask {
		private ExecutorService executorService;
		public EndSentnel(int priority, ExecutorService executorService) {
			super(priority);
			this.executorService = executorService;
			// TODO 自动生成的构造函数存根
		}
		
		public void run() {
			for(PrioritizedTask pt : List) {
				System.out.println(pt.summary());
			}
			System.out.println(this + " Calling shutdownNow()");
			executorService.shutdownNow();
		}
		
	}
}


class PrioritiedTaskProducer implements Runnable {
	private Random random = new Random();
	private PriorityBlockingQueue<Runnable> queue;
	ExecutorService executorService;
	public PrioritiedTaskProducer(PriorityBlockingQueue<Runnable> queue, ExecutorService executorService) {
		// TODO 自动生成的构造函数存根
		this.queue = queue;
		this.executorService = executorService;
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		queue.put(new PrioritizedTask.EndSentnel(-1, executorService));
		for (int i = 0; i < 10; i++) {
			queue.put(new PrioritizedTask(random.nextInt(10)));
			Thread.yield();
		}
		try {
			for (int i = 0; i < 10; i++) {
				TimeUnit.MILLISECONDS.sleep(1);
				queue.put(new PrioritizedTask(10));
			}
			for (int i = 0; i < 10; i++) {
				 queue.put(new PrioritizedTask(i));
			 }
			 
			 
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
		System.out.println("Finished PrioritiedTaskProducer");
	}
}

class PrioritiedTaskConsumer implements Runnable {
	private PriorityBlockingQueue<Runnable> queue;
	public PrioritiedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
		// TODO 自动生成的构造函数存根
		this.queue = queue;
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		try {
			while (!Thread.interrupted()) {
				queue.take().run();
			}
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
		System.out.println("Finished PrioritiedTaskConsumer");
	}
}
public class PriorityBlockingQueueDemo {
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new PrioritiedTaskProducer(queue, executorService));
		TimeUnit.SECONDS.sleep(2);
		executorService.execute(new PrioritiedTaskConsumer(queue));
	}
}
