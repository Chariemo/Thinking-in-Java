package interfaces;

public class Apply {
	public static void process(Processor p, Object s) {
		System.out.println("Using Process " + p.name());
		System.out.println(p.process(s));
	}
}
