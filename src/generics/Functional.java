package generics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

interface Combiner<T> {
	T combine(T x, T y);
}

interface UnaryFunction<R, T> {
	R function(T x);
}

interface Collector<T> extends UnaryFunction<T, T> {
	T result();
}

interface UnaryPredicate<T> {
	boolean test(T x);
}


public class Functional {
	public static <T> T reduce(Iterable<T> seq, Combiner<T> combiner) {
		Iterator<T> iterator = seq.iterator();
		if (iterator.hasNext()) {
			T result = iterator.next();
			while (iterator.hasNext()) {
				result = combiner.combine(result, iterator.next());
			}
			return result;
		}
		return null;
	}
	
	public static <T> Collector<T> forEach(Iterable<T> seq, Collector<T> func) {
		for (T t : seq) {
			func.function(t);
		}
		return func;
	}
	
	public static <R,T> List<R> transform(Iterable<T> seq, UnaryFunction<R,T> function) {
		List<R> result = new ArrayList<R>();
		for (T t : seq) {
			result.add(function.function(t));
		}
		return result;
	}
	
	public static <T> List<T> Filter(Iterable<T> seq, UnaryPredicate<T> predicate) {
		List<T> result = new ArrayList<>();
		for (T t : seq) {
			if (predicate.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
	
	static class IntegerAdder implements Combiner<Integer> {
		public Integer combine(Integer x, Integer y) {
			return x + y;
		}
	}
	
	static class IntegerSubtracter implements Combiner<Integer> {
		public Integer combine(Integer x, Integer y) {
			return x - y;
		}
	}
	
	static class BigDecimalAdder implements Combiner<BigDecimal> {
		public BigDecimal combine(BigDecimal x, BigDecimal y) {
			return x.add(y);
		}
	}
	
	static class BigIntegerAdder implements Combiner<BigInteger> {
		public BigInteger combine(BigInteger x, BigInteger y) {
			return x.add(y);
		}
	}
	
	static class AtomicLongAdder implements Combiner<AtomicLong> {
		public AtomicLong combine(AtomicLong x, AtomicLong y) {
			return new AtomicLong(x.addAndGet(y.get()));
		}
	}
	
	static class BigDecimalUlp implements UnaryFunction<BigDecimal, BigDecimal> {
		public BigDecimal function(BigDecimal x) {
			return x.ulp();
		}
	}
	
	static class GreaterThan<T extends Comparable<T>> implements UnaryPredicate<T> {
		private T bound;
		public GreaterThan(T bound) {
			this.bound = bound;
		}
		public boolean test(T x) {
			return x.compareTo(bound) > 0;
		}
	}
	
	static class MultiplyingIntegerCollector implements Collector<Integer> {
		private Integer val = 1;
		public Integer function(Integer x) {
			val *= x;
			return val;
		}
		public Integer result() {
			return val;
		}
	}
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		Integer result = reduce(list, new IntegerAdder());
		System.out.println(result);
		
		result = reduce(list, new IntegerSubtracter());
		System.out.println(result);
		
		System.out.println(Filter(list, new GreaterThan<>(4)));
		System.out.println(forEach(list, new MultiplyingIntegerCollector()).result());
		
		System.out.println(forEach(Filter(list, new GreaterThan<>(4)), new MultiplyingIntegerCollector()).result());
		
		
	}
}
 