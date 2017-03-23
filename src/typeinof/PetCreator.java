package typeinof;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class PetCreator {
	private Random random = new Random();
	
	public abstract List<Class<? extends Pet>> types();
	
	public Pet randomPet() {
		int index = random.nextInt(types().size());
		try {
			return types().get(index).newInstance();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			new RuntimeException(e);
		}
		return null;
	}
	
	public Pet[] createArray(int size) {
		Pet[] result = new Pet[size];
		for (int i = 0; i < size; i++) {
			result[i] = randomPet();
		}
		return result;
	}
	
	public ArrayList<Pet> arrayList(int size) {
		return (ArrayList<Pet>) Arrays.asList(createArray(size));
	}
}
