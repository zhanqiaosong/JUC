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
					//�ȴ�����ʿ������,դ��������ɣ��ȴ��´μ�ʱ������դ������
					cyclicBarrier.await();
					doWork();
					//�ȴ�����ʿ���������
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
			System.out.println(soliderName+":������ɡ�");			
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
				System.out.println("˾�ʿ��"+n+"����������ɣ�");
			}else {
				System.out.println("˾�ʿ��"+n+"����������ɣ�");
				flag = true;
			}
		}
	}
	public static void main(String[] args) throws Exception{
		int N = 10;
		BarrierRun barrierRun = new TestCyclicBarrier().new BarrierRun(N, false);
		Thread[] threads = new Thread[N];
		CyclicBarrier cyclicBarrier = new CyclicBarrier(N, barrierRun);
		System.out.println("���϶��飡");
		for (int i = 1; i <= N; i++) {
			System.out.println("ʿ��"+i+"������");
			threads[i-1] = new Thread(new TestCyclicBarrier().new Solider(cyclicBarrier, "ʿ��"+i));
			threads[i-1].start();
		}
	}
}
