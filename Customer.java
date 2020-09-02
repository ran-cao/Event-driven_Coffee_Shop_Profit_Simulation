import java.util.*; 
public class Customer
{
    // instance variables
    int customerID;// count and label each customer by order
    private double arrivalTime; //a time when the customer arrived
    private double serviceInterval; // the amount of time it takes to serve this customer
    private double leavingTime; // the time when the customer left
    private double serviceTime; // the time when the customer is served
    private Cashier cashier; // the cashier serving this customer

    /**
     * Constructor for objects of class Customer
     */
    public Customer(double arrivalTime,int id)
    {
        this.arrivalTime = arrivalTime;
        customerID = id;
    }
    
    // return the customer's arriving time
    public double getArrivalTime(){
        return arrivalTime;
    }
   
    // return the customer updated serving time
    public double getServiceTime(){
        return serviceTime;
    }
 
     // the time when a customer get served
    public void setServiceTime( double serviceTime ) {         
        this.serviceTime = serviceTime;
    }
    
    public void setLeavingTime( double leavingTime ) { 
        this.leavingTime = leavingTime; 
    }    
    
     // find the waiting time
    public double getWaitingTime() {       
        return serviceTime - arrivalTime;
    }
   
    // get leaving time 
    public double getLeavingTime(){
        return leavingTime;
    }

    // find out which cashier serve this customer
    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    // return customer's order
    public int getID() {
        return customerID; 
    }
}
