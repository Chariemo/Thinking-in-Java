package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class TextFile extends ArrayList<String> {
	
	public static String read(String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsolutePath()));
			try {
				String string;
				while ((string = in.readLine()) != null) {
					stringBuilder.append(string + "\n");
				}
			} finally {
				in.close();
			}
		} catch(IOException exception) {
			throw new RuntimeException();
		}
		return stringBuilder.toString();
	}

	public static void write(String fileName, String text) {
		try {
			PrintWriter out = new PrintWriter(new File(fileName).getAbsolutePath());
			try {
				out.print(text);
			} finally {
				out.close();
			}
		} catch(IOException exception) {
			throw new RuntimeException();
		}
	}
	
	public TextFile(String fileName, String splitter) {
		super(Arrays.asList(read(fileName).split(splitter)));
		if (get(0).equals("")) {
			remove(0);
		}
	}
	
	public TextFile(String fileName) {
		this(fileName, "\n");
	}
	
	public void write(String fileName) {
		try {
			PrintWriter out = new PrintWriter(new File(fileName).getAbsolutePath());
			try {
				for (String item : this) {
					out.println(item);
				}
			} finally {
				out.close();
			}
		} catch(IOException exception) {
			throw new RuntimeException();
		}
	}
	
	public static void main(String[] args) {
		String file = read("src/io/TextFile.java");
		write("src/io/text.txt", file);
		TextFile textFile = new TextFile("src/io/text.txt");
		textFile.write("src/io/text2.txt");
		TreeSet<String> words = new TreeSet<String>(new TextFile("src/io/text2.txt", "\\W+"));
		System.out.println(words.headSet("a"));
	}
}
