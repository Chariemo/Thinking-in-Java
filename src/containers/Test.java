package containers;

public abstract class Test<T>{
	String name;
	public Test(String name) {
		this.name = name;
	}
	abstract int test(T container, TestParam tp);
}
