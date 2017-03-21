package generics;

import net.mindview.util.Generator;

public class Fibonacci implements Generator<Integer> {
	private int count = 0;

	@Override
	public Integer next() {
		// TODO 自动生成的方法存根
		return fib(count++);
	}
	
	private int fib(int n) {
		if (n < 2) {
			return 1;
		}
		return fib(n - 2) + fib(n - 1);
	}
	
	public static void main(String[] args) {
		Fibonacci fibonacci = new Fibonacci();
		for (int i = 0; i < 18; i++) {
			System.out.print(fibonacci.next() + " ");
		}
	}
}
