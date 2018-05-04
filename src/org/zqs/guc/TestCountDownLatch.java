package org.zqs.guc;

/**
 * 测试倒计时器CountDownLatch
 * @author QiaosongZhan
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCountDownLatch implements Runnable{
	static final CountDownLatch countDownLatch = new CountDownLatch(10);
	static final TestCountDownLatch test_count_down_latch = new TestCountDownLatch();
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId()+" Check complete");
			countDownLatch.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			exec.submit(test_count_down_latch);
		}
		//1.倒计时开始
		//必须先await()方法，要求主线程等待
		countDownLatch.await();
		//2.倒计时结束
		System.out.println("Fire!");
		//最后
		exec.shutdown();
	}
}
