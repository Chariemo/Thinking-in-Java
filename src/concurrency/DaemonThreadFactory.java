package concurrency;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable arg0) {
		// TODO 自动生成的方法存根
		Thread thread = new Thread(arg0);
		thread.setDaemon(true);
		return thread;
	}
	
}
