package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

public class UsingBuffers {
	public static void main(String[] args) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		FileChannel fileChannel = new FileOutputStream("src/io/text.out").getChannel();
		fileChannel.write(ByteBuffer.wrap("UsingBuffers.java".getBytes()));
		fileChannel.close();
		
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		charBuffer.put("UsingBuffers.java".toCharArray());
		System.out.println(charBuffer.rewind());
	}
}
