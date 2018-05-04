package org.zqs.guc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore implements Runnable{
	final Semaphore Semaphore = new Semaphore(5); 
	@Override
	public void run() {
		try {
			Semaphore.acquire();
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId()+" is done!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Semaphore.release();
		}
	}
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(20);
		final TestSemaphore testSemaphore = new TestSemaphore();
		for (int i = 0; i < 20; i++) {
			exec.submit(testSemaphore);
		}
		exec.shutdown();
	}
}
