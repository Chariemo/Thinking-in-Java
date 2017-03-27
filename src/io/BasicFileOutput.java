package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BasicFileOutput {
	static String file = "BasicFileOutput.out";
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File("src/io/BasicfileOutput.java").getAbsolutePath()));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		int linecount = 1;
		String string;
		while ((string = in.readLine()) != null) {
			out.println(linecount++ + ": " + string);
		}
		out.close();
		System.out.println(BufferedInputFile.read(file));
	}
}
