package typeinof;

import java.util.HashMap;
import java.util.Map;


public class TypeCounter extends HashMap<Class<?>, Integer> {
	
	private Class<?> baseType;
	public TypeCounter(Class<?> baseType) {
		this.baseType = baseType;
	}
	
	public void count(Object object) {
		Class<?> type = object.getClass();
		
		if (!baseType.isAssignableFrom(type)) {
			throw new RuntimeException(object + "incorrect type: " + type + ", should be type or subtype "
					+ "of " + baseType);
		}
		countClass(type);
	}
	
	private void countClass(Class<?> type) {
		Integer quantity = get(type);
		put(type, quantity == null ? 1 : quantity + 1);
		Class<?> superClass = type.getSuperclass();
		if (superClass != null && baseType.isAssignableFrom(superClass)) {
			countClass(superClass);
		}
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("{");
		for (Map.Entry<Class<?>, Integer> pair : entrySet()) {
			stringBuilder.append(pair.getKey().getSimpleName());
			stringBuilder.append(" = ");
			stringBuilder.append(pair.getValue());
			stringBuilder.append(", ");
		}
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
}
