package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedInputFile {
	public static String read(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String string;
		StringBuilder stringBuilder = new StringBuilder();
		while ((string = in.readLine()) != null) {
			stringBuilder.append(string + "\n");
		}
		in.close();
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(read("src/io/BufferedInputFile.java"));
	}
}