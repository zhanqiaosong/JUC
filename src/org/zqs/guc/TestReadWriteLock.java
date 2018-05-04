package org.zqs.guc;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class TestReadWriteLock {
	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock reentrantReadWriteLock =  new ReentrantReadWriteLock();
	private static ReadLock readLock = reentrantReadWriteLock.readLock();
	private static WriteLock writeLock = reentrantReadWriteLock.writeLock();
	private int value;
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);
			System.out.println("read:"+value+","+(lock==this.lock));
			return value;
		} finally {
			lock.unlock();
		}
	}
	public void handleWrite(Lock lock,int val) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(1000);
			value = val;
			System.out.println("write:"+value+","+(lock==this.lock));
		} finally {
			lock.unlock();
		}
	}
	public static void main(String[] args) {
		final TestReadWriteLock demo = new TestReadWriteLock();
		Runnable readRun = new Runnable() {
			public void run() {
				try {
					demo.handleRead(readLock);
					demo.handleRead(lock);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Runnable writeRun = new Runnable() {
			public void run() {
				try {
					demo.handleWrite(writeLock,new Random().nextInt());
					demo.handleWrite(lock,new Random().nextInt());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		for (int i = 0; i < 18; i++) {
			new Thread(readRun).start();
		}
		for (int i = 0; i < 18; i++) {
			new Thread(writeRun).start();
		}
	}
}
