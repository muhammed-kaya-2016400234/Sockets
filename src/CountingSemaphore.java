
public class CountingSemaphore { // Different from the book
	 // used for synchronization of cooperating threads
	 int value; // semaphore is initialized to the number of resources
	 public CountingSemaphore(int initValue) {
	 value = initValue;
	 }
	 public synchronized void P(String name) { // blocking
	 while (value == 0)
		try {
			System.out.println(name+" is waiting");
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 System.out.println(name+" acquired");
	 value--;
	}
	 public synchronized void V(String name) { // non-blocking
	 value++;
	 System.out.println(name+" released");
	 notify();
	 }
	} 