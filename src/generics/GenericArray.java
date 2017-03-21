package generics;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GenericArray<a> {
	private a[] array;
	@SuppressWarnings("unchecked")
	public GenericArray(Class<a> type, int size) {
		array = (a[])Array.newInstance(type, size);
	}
	public void put(int index, a item) {
		array[index] = item;
	}
	
	public a get(int index) {
		return (a) array[index];
	}
	
	public a[] rep() {
		return array;
	}
	
	public static void main(String[] args) {
		GenericArray<Integer> item = new GenericArray<>(Integer.class, 10);
		for (int i = 0; i < 10; i++) {
			item.put(i, i);
		}
		
		for (int i = 0; i < 10; i++) {
			System.out.print(item.get(i) + " ");
		}
		System.out.println();
		Integer[] aIntegers = item.rep();
		System.out.println(Arrays.toString(aIntegers));
	}
}
