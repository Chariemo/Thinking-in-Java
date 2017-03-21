package concurrency;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Horse implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private int strides = 0;
	private Random random = new Random();
	private static CyclicBarrier barrier;
	public Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
	public  int getStrides() {
		return this.strides;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				strides += random.nextInt(3);
				/*synchronized (this) {
					
				}*/
				barrier.await();
			}
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		} catch(BrokenBarrierException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public String toString() {
		return "Horse " + id + " ";
	}
	
	public String tracks() {
		 StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			stringBuilder.append("*");
		}
		stringBuilder.append(id);
		return stringBuilder.toString();
	}
}

public class HorseRace {
	static final int FINISH_LINE = 75;
	private ArrayList<Horse> horses = new ArrayList<>();
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private CyclicBarrier cyclicBarrier;
	public HorseRace(int nHorses, final int pause) {
		cyclicBarrier = new CyclicBarrier(nHorses, new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().toString());
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < FINISH_LINE; i++) {
					stringBuilder.append("=");
				}
				System.out.println(stringBuilder);
				for (Horse horse: horses) {
					System.out.println(horse.tracks());
				}
				for (Horse horse : horses) {
					if (horse.getStrides() >= FINISH_LINE) {
						System.out.println(horse + "won!");
						executorService.shutdownNow();
						return;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch(InterruptedException exception) {
					System.out.println("Barrier-action sleep interrupted");
				}
			}
		});
		for (int i = 0; i < nHorses; i++) {
			Horse horse = new Horse(cyclicBarrier);
			horses.add(horse);
			executorService.execute(horse);
		}
	}
	
	public static void main(String[] args) {
		int nHorses = 7;
		int pause = 200;
		new HorseRace(nHorses, pause);
	}
}
