package thread;

///两个线程轮流打印100-1的数
public class TwoThreadPrintNum {
	public static void main(String[] args) {
		PrintRunnable p = new PrintRunnable();
		new Thread(p, "线程1").start();
		new Thread(p, "线程2").start();
	}
}

class PrintRunnable implements Runnable {
	int num = 100;

	@Override
	public void run() {
		while (num > 0) {
			synchronized (this) {
				notify();
				System.out.println("线程:" + Thread.currentThread().getName() + ":" + num--);
				try {
					wait(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}