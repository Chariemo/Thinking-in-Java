package io;

import java.awt.List;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList {
	public static void main(String[] args) {
		File path = new File("src/io");
		String[] liStrings;
		if (args.length == 0) {
			liStrings = path.list();
		}
		else {
			liStrings = path.list(new DirFilter(".*"));
		}
		Arrays.sort(liStrings, String.CASE_INSENSITIVE_ORDER);
		System.out.println(path.getAbsolutePath());
		for (String dirItem : liStrings) {
			System.out.println(dirItem);
		}
	}
}

class DirFilter implements FilenameFilter {
	private Pattern pattern;
	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);
	}
	@Override
	public boolean accept(File dir, String name) {
		// TODO 自动生成的方法存根
		return pattern.matcher(name).matches();
	}
}