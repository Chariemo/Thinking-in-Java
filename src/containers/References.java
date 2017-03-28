package containers;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;


class VeryBig {
	private static final int SIZE = 10000;
	private long[] ls = new long[SIZE];
	private String ident;
	public VeryBig(String id) {
		this.ident = id;
	}
	
	public String toString() {
		return ident;
	}
	
	protected void finalize() {
		System.out.println("Finalizing " + ident);
	}
}

public class References {
	private static ReferenceQueue<VeryBig> rQueue = new ReferenceQueue<>();
	
	public static void checkQueue() {
		Reference<? extends VeryBig> inq = rQueue.poll();
		if (inq != null) {
			System.out.println("In Queue: " + inq.get());
		}
	}
	
	public static void main(String[] args) {
		int size = 10;
		if (args.length > 0) {
			size = new Integer(args[0]);
		}
		
		LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			sa.add(new SoftReference<VeryBig>(new VeryBig("Soft " + i)));
			System.out.println("Just created: " + sa.getLast());
			checkQueue();
		}
		
		LinkedList<WeakReference<VeryBig>> wa = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			wa.add(new WeakReference<VeryBig>(new VeryBig("Weak " + i)));
			System.out.println("Just created: " + wa.getLast());
			checkQueue();
		}
		
		SoftReference<VeryBig> softReference = new SoftReference<VeryBig>(new VeryBig("Soft"));
		WeakReference<VeryBig> weakReference = new WeakReference<VeryBig>(new VeryBig("Weak"));
		System.gc();
		LinkedList<PhantomReference<VeryBig>> phantomReferences = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			phantomReferences.add(new PhantomReference<VeryBig>(new VeryBig("Phantom " + i), rQueue));
			System.out.println("Just created: " + phantomReferences.getLast());
			checkQueue();
		}
	}
}
