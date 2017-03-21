package io;

import java.util.Arrays;
import java.util.Collection;

public class PPrint {
	public static String pformat(Collection<?> collection) {
		if (collection.size()  == 0) {
			return "[]";
		}
		StringBuilder stringBuilder = new StringBuilder("[");
		for (Object object : collection) {
			if (collection.size() != 1) {
				stringBuilder.append("\n  ");
			}
			stringBuilder.append(object);
		}
		
		if (collection.size() != 1) {
			stringBuilder.append("\n");
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
	
	public static void pprint(Collection<?> collection) {
		System.out.println(pformat(collection));
	}
	
	public static void pprint(Object[] cObjects) {
		System.out.println(pformat(Arrays.asList(cObjects)));
	}
}
