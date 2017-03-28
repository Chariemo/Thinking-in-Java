package containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import net.mindview.util.CountingGenerator;
import net.mindview.util.CountingIntegerList;
import net.mindview.util.Generated;

public class ListPerformance {
	static Random random = new Random();
	static int reps = 1000;
	static List<Test<List<Integer>>> tests = new ArrayList<>();
	static List<Test<LinkedList<Integer>>> qTests = new ArrayList<>();
	static {
		tests.add(new Test<List<Integer>>("add") {
			@Override
			int test(List<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int listSize = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < listSize; j++) {
						container.add(j);
					}
				}
				return loops * listSize;
			}
			
		});
		
		tests.add(new Test<List<Integer>>("get") {
			
			@Override
			int test(List<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops * reps;
				int listSize = container.size();
				for (int i = 0; i < loops; i++) {
					container.get(random.nextInt(listSize));
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("set") {
			
			@Override
			int test(List<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops * reps;
				int listSize = container.size();
				for (int i = 0; i < loops; i++) {
					container.set(random.nextInt(listSize), 22);
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("iteradd") {
			
			@Override
			int test(List<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				final int LOOPS = 1000000;
				int half = container.size() / 2;
				ListIterator<Integer> iterator = container.listIterator(half);
				for (int i = 0; i < LOOPS; i++) {
					iterator.add(33);
				}
				return LOOPS;
			}
		});
		
		
		tests.add(new Test<List<Integer>>("insert") {
			
			@Override
			int test(List<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				for (int i = 0; i < loops; i++) {
					container.add(5, 47);
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("remove") {
			
			@Override
			int test(List<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					container.addAll(new CountingIntegerList(size));
					while (container.size() > 5) {
						container.remove(5);
					}
				}
				return loops * size;
			}
		});
		
		qTests.add(new Test<LinkedList<Integer>>("addFirst") {
			
			@Override
			int test(LinkedList<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.addFirst(47);
					}
				}
				return loops * size;
			}
		});
		
		qTests.add(new Test<LinkedList<Integer>>("addLast") {
			
			@Override
			int test(LinkedList<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.addLast(49);
					}
				}
				return loops * size;
			}
		});
		
		qTests.add(new Test<LinkedList<Integer>>("rmFirst") {
			
			@Override
			int test(LinkedList<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					container.addAll(new CountingIntegerList(size));
					while (container.size() > 0) {
						container.removeFirst();
					}
				}
				return loops * size;
			}
		});
		
		
		qTests.add(new Test<LinkedList<Integer>>("rmLast") {
			
			@Override
			int test(LinkedList<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					container.addAll(new CountingIntegerList(size));
					while (container.size() > 0) {
						container.removeLast();
					}
				}
				return loops * size;
			}
		});
		
		
	}
	
	static class ListTester extends Tester<List<Integer>> {
		public ListTester(List<Integer> container, List<Test<List<Integer>>> tests) {
			super(container, tests);
		}
		
		protected List<Integer> initialize(int size) {
			container.clear();
			container.addAll(new CountingIntegerList(size));
			return container;
		}
		
		public static void run(List<Integer> list, List<Test<List<Integer>>> tests) {
			new ListTester(list, tests).timedTest();
		}
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			Tester.defaultParams = TestParam.array(args);
		}
		
		Tester<List<Integer>> arrayTest = new Tester<List<Integer>>(null, tests.subList(1, 3)) {
			protected List<Integer> initialize(int size) {
				Integer[] ia = Generated.array(Integer.class, new CountingGenerator.Integer(), size);
				return Arrays.asList(ia);
			}
		};
		
		arrayTest.setheadLine("Array as List");
		arrayTest.timedTest();
		Tester.defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
		if (args.length > 0) {
			Tester.defaultParams = TestParam.array(args);
		}
		ListTester.run(new ArrayList<>(), tests);
		ListTester.run(new LinkedList<>() , tests);
		ListTester.run(new Vector<>(), tests);
		
		Tester.fieldWidth = 12;
		Tester<LinkedList<Integer>> qTester = new Tester<LinkedList<Integer>>(new LinkedList<Integer>()
				, qTests);
		qTester.setheadLine("Queue tests");
		qTester.timedTest();
	}
}
