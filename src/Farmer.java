import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Farmer implements Runnable {

	private static int farmerCount;
	private static Lock road = new ReentrantLock();
	private int id;
	private String direction;
	
	public Farmer(String direction) {
		id = ++farmerCount;
		this.direction = direction;
	}
	
	private void declareEntry() {
		System.out.println(direction+" Farmer "+id+" is in the tunnel.");
	}
	
	private void declareExit() {
		System.out.println(direction+" Farmer "+id+" is exiting the tunnel.");
	}
	
	
	private void crossRoad() {
		
		System.out.println(direction+" Farmer "+id+" wants to enter the tunnel.");
		while(!road.tryLock());
		declareEntry();
		try {
			Thread.sleep((int)(5000 * Math.random()) + 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			declareExit();
			road.unlock();
		}
	}
	
	@Override
	public void run() {
		
		crossRoad();

	}

}
