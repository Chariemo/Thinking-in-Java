package containers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetPerformance {
	static List<Test<Set<Integer>>> seTests = new ArrayList<Test<Set<Integer>>>();
	
	static {
		seTests.add(new Test<Set<Integer>>("add") {
			
			@Override
			int test(Set<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.add(j);
					}
				}
				return loops * size;
			}
		});
		
		seTests.add(new Test<Set<Integer>>("contains") {
			
			@Override
			int test(Set<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops;
				int span = tp.size * 2;
				for (int i = 0; i < loops; i++) {
					for (int j = 0 ; j < span; j++) {
						container.contains(j);
					}
				}
				return loops * span;
			}
		});
		
		seTests.add(new Test<Set<Integer>>("iterate") {
			
			@Override
			int test(Set<Integer> container, TestParam tp) {
				// TODO Auto-generated method stub
				int loops = tp.loops * 10;
				for (int i = 0; i < loops; i++) {
					Iterator<Integer> iterator = container.iterator();
					while (iterator.hasNext()) {
						iterator.next();
					}
				}
				return loops * container.size();
			}
		});
		
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			Tester.defaultParams = TestParam.array(args);
		}
		
		Tester.fieldWidth = 10;
		Tester.run(new TreeSet<Integer>(), seTests);
		Tester.run(new HashSet<>(), seTests);
		Tester.run(new LinkedHashSet<>(), seTests);
		
	}
}
