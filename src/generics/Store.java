package generics;

import java.util.ArrayList;
import java.util.Random;

  import net.mindview.util.Generator;

class Product {
	private final int id;
	private String description;
	private double price;
	public Product(int IDNumber, String descr, double price) {
		this.id = IDNumber;
		this.description = descr;
		this.price = price;
		System.out.println(this);
	}
	
	public String toString() {
		return id + ": " + description + ", price: " + price;
	}
	
	public void priceChange(double change) {
		this.price += change;
	}
	
	public static Generator<Product> generator = new Generator<Product>() {
		private Random random = new Random();
		
		@Override
		public Product next() {
			// TODO 自动生成的方法存根
			return new Product(random.nextInt(1000), "test", Math.round(random.nextDouble() * 1000.0) + 0.99); 
		}
	};
	
}

class Shelf extends ArrayList<Product> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Shelf(int nProducts) {
		Generators.fill(this, Product.generator, nProducts);
	}
}

class Aisle extends ArrayList<Shelf> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Aisle(int nShelfs, int nProducts) {
		for (int i = 0; i < nShelfs; i++) {
			add(new Shelf(nProducts));
		}
	}
}

class CheckOutStand {
	
}

class Office {
	
}

@SuppressWarnings("serial")
public class Store extends ArrayList<Aisle>{
	@SuppressWarnings("unused")
	private ArrayList<CheckOutStand> checkOutStands = new ArrayList<CheckOutStand>();
	@SuppressWarnings("unused")
	private Office office = new Office();
	public Store(int nAisles, int nShelfs, int nProducts) {
		for (int i = 0; i < nAisles; i++) {
			add(new Aisle(nShelfs, nProducts));
		}
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Aisle aisle : this) {
			for (Shelf shelf : aisle) {
				for (Product product : shelf) {
					stringBuilder.append(product);
					stringBuilder.append("\n");
				}
			}
		}
		return stringBuilder.toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Store(5, 1, 3));
	}
}
