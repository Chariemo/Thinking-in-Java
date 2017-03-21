package sortAlgorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Sort implements Runnable {
	private ArrayList<Integer> list;
	private ExecutorService executorService;
	private int start, end;
	private int index;
	public Sort(ExecutorService executorService, ArrayList<Integer> list, int start, int end) {
		this.executorService = executorService;
		this.list = list;
		this.end = end;
		this.start = start;	
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		quickSort();
	}
	
	private void quickSort () {
		if (start < end) {
			partition(list, start, end);
			executorService.execute(new Sort(executorService, list, start, index - 1));			
			executorService.execute(new Sort(executorService, list, index + 1, end));
		}
	}
	
	private void partition(ArrayList<Integer> list, int start, int end) {
		int key = list.get(end);
		int indexOfLow = start - 1;
		int temp;
		for (int i = start; i < end; i++) {
			if (list.get(i) <= key) {
				indexOfLow++;
				temp = list.get(indexOfLow);
				list.set(indexOfLow, list.get(i));
				list.set(i, temp);
			}
		}
		temp = list.get(indexOfLow + 1);
		list.set(indexOfLow + 1, key);
		list.set(end, temp);
		index = indexOfLow + 1;	
	}
	
	
}

public class QuickSortDemo {
	
	public static void myPrint(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws InterruptedException {
		int N = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(N);
		Random random = new Random();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10000; i++) {
			list.add(random.nextInt(20) - 10);
		}
		myPrint(list);
		executorService.execute(new Sort(executorService, list, 0, list.size() - 1));
		TimeUnit.SECONDS.sleep(3);
		myPrint(list);
	}
}
