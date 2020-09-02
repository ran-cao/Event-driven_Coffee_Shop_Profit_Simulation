import java.util.*; 
public class Cashier {
	private double serviceTime;// service time for each cashier
	private boolean available;//cashier's availibility status
	private static int count = 0;// initially, the number of cashier is 0

	public Cashier(double serviceTime,boolean available) {
		this.serviceTime = serviceTime;
		this.available = available;
	}
     
	 // when the cashier take the customer, turn the availability to be false
	public void takeCustomer() {
		available = false;
	}

	//when the cashier finish serving, turn the availability to be false	
	public void finishServing() {
		available = true;
	}

	// return available status
	public boolean isAvailable() {
		return true;
	}

	//return the service time for each cashier
	public double getServiceTime() {
		return serviceTime;
	}
}