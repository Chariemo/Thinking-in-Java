package typeinof;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class LiteralPetCreator extends PetCreator {
	
	public static final List<Class<? extends Pet>> allTypes = 
			Collections.unmodifiableList(Arrays.asList(Pet.class, Dog.class, Mutt.class,
					Pug.class));
	
	private static final List<Class<? extends Pet>> types =
			allTypes.subList(allTypes.indexOf(Mutt.class), allTypes.size());
	@Override
	public List<Class<? extends Pet>> types() {
		// TODO 自动生成的方法存根
		return types;
	}
	
	public static void main(String[] args) {
		System.out.println(types);
	}
}
