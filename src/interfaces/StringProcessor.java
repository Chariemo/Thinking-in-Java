package interfaces;

import java.util.Arrays;

public class StringProcessor implements Processor {
	public String name() {
		return getClass().getSimpleName();
	}
	public String process(Object input) {
		return "process StringProcessor";
	};
	public static String s = "If she weighs the same ans a duck, she's made of wood";
	public static void main(String[] args) {
		Apply.process(new Upcase(), s);
		Apply.process(new Downcase(), s);
		Apply.process(new Splitter(), s);
	}
}

class Upcase extends StringProcessor {
	public String process(Object input) {
		return ((String)input).toUpperCase();
	}
}

class Downcase extends StringProcessor {
	public String process(Object input) {
		return ((String)input).toLowerCase();
	}
}

class Splitter extends StringProcessor {
	public String process(Object input) {
		return Arrays.toString(((String)input).split(" "));
	}
}