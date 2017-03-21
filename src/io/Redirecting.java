package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Redirecting {
	public static void main(String[] args) throws IOException{
		PrintStream console = System.out;
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("src/io/Redirecting.java"));
		PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("src/io/test.out")));
		System.setIn(inputStream);
		System.setOut(out);
		System.setErr(out);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String string;
		while ((string = bufferedReader.readLine()) != null) {
			System.out.println(string);
		}
		out.close();
		System.setOut(console);
	}
}
