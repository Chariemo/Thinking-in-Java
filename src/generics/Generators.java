package generics;

import java.util.Collection;

import net.mindview.util.Generator;


public class Generators {
	public static <T> Collection<T> fill(Collection<T> coll, Generator<T> generator, int n) {
		for (int i = 0; i < n; i++) {
			coll.add(generator.next());
		}
		return coll;
	}
}
