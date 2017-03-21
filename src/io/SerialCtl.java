package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerialCtl implements Serializable {
	private String aString;
	private transient String bString;
	public SerialCtl(String aString, String bString) {
		this.aString = "Not transient " + aString;
		this.bString = "transient " + bString;
	}
	
	public String toString() {
		return aString + "\n" + bString;
	}
	
	private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
		objectInputStream.defaultReadObject();
		bString = (String)objectInputStream.readObject();
	}
	
	private void writeObject(ObjectOutputStream objectOutputStream) throws ClassNotFoundException, IOException {
		objectOutputStream.defaultWriteObject();
		objectOutputStream.writeObject(bString);
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		SerialCtl serialCtl = new SerialCtl("000", "111");
		System.out.println("before: \n" + serialCtl);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/io/serial.txt"));
		objectOutputStream.writeObject(serialCtl);
		objectOutputStream.close();
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/io/serial.txt"));
		SerialCtl serialCtl2 = (SerialCtl)objectInputStream.readObject();
		System.out.println("After: \n" + serialCtl2);
	}
	
}
