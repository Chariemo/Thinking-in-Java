package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompress {
	public static void main(String[] args) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File("src/io/test.zip"));
		CheckedOutputStream csum = new CheckedOutputStream(fileOutputStream, new Adler32());
		ZipOutputStream zipOutputStream = new ZipOutputStream(csum);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
		zipOutputStream.setComment("This is a test of Java Zipping");
		for (String arg : args) {
			System.out.println("Writting file " + arg);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(arg));
			zipOutputStream.putNextEntry(new ZipEntry(arg));
			int c;
			while ((c = bufferedReader.read()) != -1) {
				bufferedOutputStream.write(c);
			}
			bufferedReader.close();
			bufferedOutputStream.flush();
		}
		bufferedOutputStream.close();
		System.out.println("Checksum: " + csum.getChecksum().getValue());
		System.out.println("Reading file");
		FileInputStream fileInputStream = new FileInputStream(new File("src/io/test.zip"));
		CheckedInputStream checkedInputStream = new CheckedInputStream(fileInputStream, new Adler32());
		ZipInputStream zipInputStream = new ZipInputStream(checkedInputStream);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
		ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			System.out.println("Reading file " + zipEntry);
			int x;
			while ((x = bufferedInputStream.read()) != -1) {
				System.out.write(x);
			}
		}
		
		if (args.length == 1) {
			System.out.println("Checksum: " + checkedInputStream.getChecksum().getValue());
		}
		bufferedInputStream.close();
		ZipFile zipFile = new ZipFile(new File("src/io/test.zip"));
		Enumeration enumeration = zipFile.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry2 = (ZipEntry) enumeration.nextElement();
			System.out.println("File " + zipEntry2);
		}
	}
}
