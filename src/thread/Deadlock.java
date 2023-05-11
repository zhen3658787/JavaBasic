package thread;

public class Deadlock {
	public static void main(String[] args) {
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();

		new Thread() {
			public void run() {
				synchronized (s1) {
					s1.append(1);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (s2) {
						s2.append(2);
					}
				}
				System.out.println(s1.toString());
				System.out.println(s2.toString());
			};
		}.start();
		new Thread() {
			public void run() {
				synchronized (s2) {
					s2.append("a");
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (s1) {
						s1.append("b");
					}
				}
				System.out.println(s1.toString());
				System.out.println(s2.toString());
			};
		}.start();
	}
}
