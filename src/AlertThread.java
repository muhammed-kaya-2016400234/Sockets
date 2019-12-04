
public class AlertThread extends Thread{

	Item i;
	public AlertThread(Item i) {
		this.i=i;
	}
	
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FCMNotification.pushFCMNotification(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
