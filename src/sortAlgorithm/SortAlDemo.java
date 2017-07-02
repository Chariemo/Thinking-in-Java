package sortAlgorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.sun.corba.se.impl.orbutil.closure.Future;

public class SortAlDemo {
	
	//冒泡排序
	public static void bubbleSort(ArrayList<Integer> list) {
		int high = list.size() - 1;
		int low = 0;
		int temp, i;
		while (high > low) {
			for (i = low; i < high; i++) { //取最大值
				if (list.get(i) > list.get(i + 1)) {
					temp = list.get(i);
					list.set(i, list.get(i + 1));
					list.set(i + 1, temp);	
				}
			}
			high--;
			for (i = high; i > 0; i--) { //取最小值
				if (list.get(i) < list.get(i - 1)) {
					temp = list.get(i);
					list.set(i, list.get(i - 1));
					list.set(i - 1, temp);
				}
			}
			low++;
		}
	}
	
	//插入排序
	public static void insertionSort(ArrayList<Integer> list) {
		int index, temp, key;
		int length = list.size();
		for (key = 1; key < length; ++key) {
			temp = list.get(key);
			index = key - 1;
			while (index >= 0 && list.get(index) > temp) {
				list.set(index + 1, list.get(index));
				index--;
			}
			list.set(index + 1, temp);
		}
	}
	
	//归并排序
	public static void merge(ArrayList<Integer> list, int start, int mid, int end) {
		int sizeOfA = mid - start + 1,
				sizeOfB = end - mid;
		ArrayList<Integer> listA = new ArrayList<Integer>(),
				listB = new ArrayList<Integer>();
		int indexOfA = 0, 
				indexOfB = 0, 
				index = start;
		//初始化数组A
		for (int i = 0; i < sizeOfA; i++) {
			listA.add(i, list.get(start + i));
		}
		listA.add(sizeOfA, Integer.MAX_VALUE);  //哨兵
		
		//初始化数组B
		for (int i = 0; i < sizeOfB; i++) {
			listB.add(i, list.get(mid + i + 1));
		}
		listB.add(sizeOfB, Integer.MAX_VALUE);
		
		while (index <= end) {
			if (listA.get(indexOfA) > listB.get(indexOfB)) {
				list.set(index++, listB.get(indexOfB++));
			}
			else if (listA.get(indexOfA) < listB.get(indexOfB)) {
				list.set(index++, listA.get(indexOfA++));
			}
			else if (listA.get(indexOfA) == listB.get(indexOfB)) {
				list.set(index++, listA.get(indexOfA++));
				list.set(index++, listB.get(indexOfB++));
			}
		}
	}

	public static void mergeSort(ArrayList<Integer> list, int start, int end) {
		if (start < end) {
			int mid = (int) (start + end) / 2;
			mergeSort(list, start, mid);
			mergeSort(list, mid + 1, end);
			merge(list, start, mid, end);
		}
	}
	
	//快排
	public static int partition(ArrayList<Integer> list, int start, int end) {
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
		return indexOfLow + 1;
	}
	
	public static void quickSort(ArrayList<Integer> list, int start, int end) {
		if (start < end) {
			int index = partition(list, start, end);
			quickSort(list, start, index - 1);
			quickSort(list, index + 1, end);
		}
	}
	
	
	//维护堆
	public static void maxHeapIfy(ArrayList<Integer> list, int heapSize, int top) {
		int leaftChild = top * 2 + 1,
				rightChild = top * 2 + 2;
		int largest = top, temp;
		if (leaftChild <= heapSize - 1 && list.get(top) < list.get(leaftChild)) {
			largest = leaftChild;
		}
		if (rightChild  <= heapSize - 1 && list.get(largest) < list.get(rightChild)) {
			largest = rightChild;
		}
		if (largest != top) {
			temp = list.get(top);
			list.set(top, list.get(largest));
			list.set(largest, temp);
			maxHeapIfy(list, heapSize, largest);
		}
	}
	
	//建堆
	public static void buildMaxHeap(ArrayList<Integer> list, int heapSize) {
		for (int i = (int) (heapSize - 2)/ 2; i >= 0; i--) {
			maxHeapIfy(list, heapSize, i);
		}
	}
	
	//堆排序
	public static void heapSort(ArrayList<Integer> list) {
		int heapSize = list.size();
		buildMaxHeap(list, heapSize);
		int temp;
		for (int i = heapSize - 1; i >= 1; i--) {
			temp = list.get(0);
			list.set(0, list.get(i));
			list.set(i, temp);
			heapSize--;
			maxHeapIfy(list, heapSize, 0);
		}
	}
	
	
	public static void myPrint(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Random random = new Random();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 50; i++) {
			list.add(random.nextInt(20) - 10);
		}
		myPrint(list);
		quickSort(list, 0, list.size() - 1);
		myPrint(list);
	}

	
}
