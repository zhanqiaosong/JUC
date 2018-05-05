package org.zqs.guc;

import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {
	public class Solider implements Runnable{
		private CyclicBarrier cyclicBarrier;
		private String soliderName;
		public Solider(CyclicBarrier cyclicBarrier,String soliderName) {
			this.cyclicBarrier = cyclicBarrier;
			this.soliderName = soliderName;
		}
		@Override
		public void run() {
				try {
					//等待所有士兵到齐,栅栏任务完成，等待下次计时，调用栅栏方法
					cyclicBarrier.await();
					doWork();
					//等待所有士兵完成任务
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		private void doWork() {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(soliderName+":任务完成。");			
		}
	}
	
	public class BarrierRun implements Runnable{
		private int n;
		private boolean flag;
		public BarrierRun(int n,boolean flag) {
			this.n = n;
			this.flag = flag;
		}
		@Override
		public void run() {
			if (flag) {
				System.out.println("司令：士兵"+n+"个，任务完成！");
			}else {
				System.out.println("司令：士兵"+n+"个，集合完成！");
				flag = true;
			}
		}
	}
	public static void main(String[] args) throws Exception{
		int N = 10;
		BarrierRun barrierRun = new TestCyclicBarrier().new BarrierRun(N, false);
		Thread[] threads = new Thread[N];
		CyclicBarrier cyclicBarrier = new CyclicBarrier(N, barrierRun);
		System.out.println("集合队伍！");
		for (int i = 1; i <= N; i++) {
			System.out.println("士兵"+i+"报道！");
			threads[i-1] = new Thread(new TestCyclicBarrier().new Solider(cyclicBarrier, "士兵"+i));
			threads[i-1].start();
		}
	}
}
