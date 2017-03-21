package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import concurrency.NewVarArgs;

class Data implements Serializable {
	private int n;
	public Data(int n) {
		this.n = n;
	}
	public String toString() {
		return Integer.toString(n);
	}
}
public class Worm implements Serializable{
	private static Random random = new Random();
	private Data[] datas = {new Data(random.nextInt(10)), 
			new Data(random.nextInt(10)), 
			new Data(random.nextInt(10))
	};
	private Worm next;
	private char c;
	public Worm(int i, char c) {
		System.out.println("Worm constructor " + i);
		this.c = c;
		if (--i > 0) {
			next = new Worm(i, (char)(c + 1));
		}
	}
	public Worm() {
		System.out.println("Default constructor");
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(":");
		stringBuilder.append(c);
		stringBuilder.append("(");
		for (Data data : datas) {
			stringBuilder.append(data);
		}
		stringBuilder.append(")");
		if (next != null) {
			stringBuilder.append(next);
		}
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {
		Worm worm = new Worm(6, 'a');
		System.out.println("w = " + worm);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/io/worm.out"));
		objectOutputStream.writeObject("Worm storage\n");
		objectOutputStream.writeObject(worm);
		objectOutputStream.close();
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/io/worm.out"));
		String string = (String)objectInputStream.readObject();
		Worm worm2 = (Worm)objectInputStream.readObject();
		System.out.println(string + " w2 = " + worm2); 
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream2.writeObject("Worm storage\n");
		objectOutputStream2.writeObject(worm);
		objectOutputStream2.flush();
		ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		string = (String)objectInputStream2.readObject();
		Worm worm3 = (Worm)objectInputStream2.readObject();
		System.out.print(string + " w3 = " + worm3);
	}
}
