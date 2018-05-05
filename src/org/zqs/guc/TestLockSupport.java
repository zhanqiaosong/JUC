package org.zqs.guc;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {
	private static Object object = new Object();
	private static ChangeObjectThread chageObjectThread1 = new ChangeObjectThread("t1");
	private static ChangeObjectThread chageObjectThread2 = new ChangeObjectThread("t2");
	
	public static class ChangeObjectThread extends Thread{
		public ChangeObjectThread(String name) {
			setName(name);
		}
		@Override
		public void run() {
			synchronized (object) {
				System.out.println("in "+getName());
				LockSupport.park();
				System.out.println("over "+getName());
			}
		}
	}

	public static void main(String[] args) throws Exception{
		chageObjectThread1.start();
		Thread.sleep(100);
		chageObjectThread2.start();
		LockSupport.unpark(chageObjectThread1);
		LockSupport.unpark(chageObjectThread2);
		/*chageObjectThread1.join();
		chageObjectThread2.join();*/
	}
}
