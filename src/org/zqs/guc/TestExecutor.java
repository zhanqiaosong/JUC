package org.zqs.guc;

/**
 * ���Ե���ʱ��CountDownLatch
 * @author QiaosongZhan
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExecutor implements Runnable{
	static final CountDownLatch countDownLatch = new CountDownLatch(10);
	static final TestExecutor test_count_down_latch = new TestExecutor();
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
		//1.����ʱ��ʼ
		//������await()������Ҫ�����̵߳ȴ�
		countDownLatch.await();
		//2.����ʱ����
		System.out.println("Fire!");
		//���
		exec.shutdown();
	}
}
