import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FarmerTest {

	private static final int FARMER_COUNT = 50;
	
	public static void main(String[] args) {
		
		FarmerTest obj = new FarmerTest();
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
		
		// submit farmers to thread pool
		for(int i = 0; i < farmers.size(); i++) {
			pool.submit(farmers.get(i));
			try {
				Thread.sleep((int)(1000 * Math.random()) + 1); //simulate offset arrival
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
}
