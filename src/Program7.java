//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  Program #:     7
//
//  File Name:     Program7.java
//
//  Course:        COSC-4302 Operating Systems
//
//  Due Date:      04/19/2022
//
//  Instructor:    Fred Kumi 
//
//  Chapter:       8
//
//  Description:   A class to test synchronization among threads. Each thread
//                 represents a farmer attempting to cross a single-lane bridge.
//                 A thread (farmer) must acquire a lock on the shared resource
//                 before it is allowed to continue processing.
//
//*********************************************************************

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program7 {

	private static final int FARMER_COUNT = 50;
	
	public static void main(String[] args) {
		
		Program7 obj = new Program7();
		obj.developerInfo();
		
		List<Farmer> farmers = new ArrayList<>();
		int coreCount = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(coreCount);
		
		String direction = "";
		// add runnable farmers to list
		for(int i = 0; i < FARMER_COUNT; i++) {
			if(i % 2 == 0) {
				direction = "NorthBound";
			} else {
				direction = "SouthBound";
			}
			farmers.add(new Farmer(direction));
		}
		
		System.out.println("\nFarmers arriving to the tunnel...\n");
		
		// submit farmers to thread pool
		for(int i = 0; i < farmers.size(); i++) {
			pool.submit(farmers.get(i));
		}
		
		// shutdown
		pool.shutdown();
		try {
			pool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Jeremy Aubrey");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: 7");

    }// end developerInfo method
    
}// end Program7 class