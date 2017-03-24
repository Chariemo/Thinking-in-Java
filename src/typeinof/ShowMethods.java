package typeinof;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;


public class ShowMethods {
	private static  String usage = "usage: \n" +
			"ShowMethods qualified.class.name\n" + "To show all methods in class or: \n" + 
			"ShowMethods qualified.class.name word\n" + "To search for methods involving 'word'";
	
	private static Pattern pattern = Pattern.compile("\\w+\\.");
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println(usage);
			System.exit(-1);
		}
		int lines = 0;
		try {
			Class<?> class1 = Class.forName(args[0]);
			Method[] methods = class1.getMethods();
			Constructor[] ctors = class1.getConstructors();
			if (args.length == 1) {
				for (Method method : methods) {
					System.out.println(pattern.matcher(method.toString()).replaceAll(""));
				}
				for (Constructor ctor : ctors) {
					System.out.println(pattern.matcher(ctor.toString()).replaceAll(""));
				}
				lines = methods.length + ctors.length;
			}
			else {
				for (Method method : methods) {
					if (method.toString().indexOf(args[1]) != -1) {
						System.out.println(pattern.matcher(method.toString()).replaceAll(""));
						lines++;
					}
				}
				for (Constructor ctor : ctors) {
					if (ctor.toString().indexOf(args[1]) != -1) {
						System.out.println(pattern.matcher(ctor.toString()).replaceAll(""));
						lines++;
					}
				}
			}
		} catch(ClassNotFoundException exception) {
			System.out.println("No such class: " + exception);
		}
	}
}
