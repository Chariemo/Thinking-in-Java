package io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class Directory {
	public static File[]  local(File dir, final String regex) {
		return dir.listFiles(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			@Override
			public boolean accept(File dir, String name) {
				// TODO 自动生成的方法存根
				return pattern.matcher(new File(name).getName()).matches();
			}
		});
	}
	
	public static File[] local(String name, final String regex) {
		return local(new File(name), regex);
	}
	
	public static class TreeInfo implements Iterable<File> {
		public ArrayList<File> files = new ArrayList<File>();
		public ArrayList<File> dirs = new ArrayList<File>();
		
		@Override
		public Iterator<File> iterator() {
			// TODO 自动生成的方法存根
			return files.iterator();
		}
		
		void addAll(TreeInfo other) {
			files.addAll(other.files);
			dirs.addAll(other.dirs);
		}
		
		public String toString() {
			return "dirs: " + PPrint.pformat(dirs) + "\n\nfiles: "	+ PPrint.pformat(files);
		}
	}
	
	public static TreeInfo walk(String start, String regex) {
		return recurseDirs(new File(start), regex);
	}
	
	public static TreeInfo walk(File start, String regex) {
		return recurseDirs(start, regex);
	}
	
	public static TreeInfo walk(String start) {
		return recurseDirs(new File(start), ".*");
	}
	
	public static TreeInfo walk(File start) {
		return recurseDirs(start, ".*");
	}
	
	static TreeInfo recurseDirs(File startDir, String regex) {
		TreeInfo result = new TreeInfo();
		for (File item : startDir.listFiles()) {
			if (item.isDirectory()) {
				result.dirs.add(item);
				result.addAll(recurseDirs(item, regex));
			}
			else {
				if (item.getName().matches(regex)) {
					result.files.add(item);
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println(walk("src/"));
		}
		else {
			for (String string : args) {
				System.out.println(walk(string));
			}
		}
	}
}
