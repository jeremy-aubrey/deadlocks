//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     7
//
//  File Name:     Farmer.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      04/19/2022
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       8
//
//  Description:   A runnable class that represents either a NorthBound
//                 or SouthBound farmer competing for a chance to cross a 
//                 single-lane bridge. The thread must acquire a lock in 
//                 order to proceed across the bridge.
//
//*********************************************************************

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Farmer implements Runnable {

	private static int farmerCount;                         // total farmer count (used for ids)
	private static Lock roadLock = new ReentrantLock();     // must be acquired to cross bridge
	private int id;                                         // used to ID the farmer
	private String direction;                               // northbound or southbound
	
	// constructor
	public Farmer(String direction) {
		id = ++farmerCount;
		this.direction = direction;
	}
	
    //***************************************************************
    //
    //  Method:       declareEntry (Non Static)
    // 
    //  Description:  Displays a message that the farmer is currently in 
    //                the tunnel. (Has acquired the shared resource).
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	private void declareEntry() {
		System.out.println(direction+" Farmer "+id+" is in the tunnel.");
	}// end declareEntry method
	
    //***************************************************************
    //
    //  Method:       declareExit (Non Static)
    // 
    //  Description:  Displays a message that the farmer has exited the tunnel.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	private void declareExit() {
		System.out.println(direction+" Farmer "+id+" is exiting the tunnel.");
	}// end declareExit method
	
    //***************************************************************
    //
    //  Method:       crossRoad (Non Static)
    // 
    //  Description:  Attempts to acquire a lock until it has been acquired.
    //                Once a lock is acquired, the thread simulates the farmer
    //                crossing the tunnel by calling a helper sleep method.
    //                The lock is guaranteed to be released.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	private void crossRoad() {
		
		System.out.println(direction+" Farmer "+id+" wants to enter the tunnel.");
		while(!roadLock.tryLock());
		declareEntry();
		try {
			randomSleep(4); // max of 4 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			declareExit();
			roadLock.unlock();
		}
	}// end crossRoad method
	
    //***************************************************************
    //
    //  Method:       randomSleep (Non Static)
    // 
    //  Description:  A helper method that causes the current thread 
    //                to sleep for a random period of time up to the 
    //                specified maxSeconds argument.
    //
    //  Parameters:   int (in seconds)
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	private void randomSleep(int maxSeconds) throws InterruptedException{
		int milliseconds = 1000; // 1 second default
		if(maxSeconds > 1) {
			milliseconds = maxSeconds * 1000;
		}
		Thread.sleep((int)(milliseconds * Math.random()) + 1);
		
	}// end randomSleep method
	
    //***************************************************************
    //
    //  Method:       run (Non Static)
    // 
    //  Description:  Allows the crossRoad method to run in a new thread.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	@Override
	public void run() {
		
		crossRoad();

	}// end run method

}// end Farmer class