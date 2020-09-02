import java.util.*;
public class EventsSetup implements Comparable<EventsSetup>
{
    private int numberOfCustomers; // the number of customers
    private double time; // the time when customers arrives
    private int name;
    private int eventType; // customers are waiting in line or being served
    private int arrival = 1;
    private int departure = 2; 
    private Customer customer;
    // constructor
    public EventsSetup(Customer customer, double time, int type){
        this.customer = customer;
        this.time = time;
        eventType = type;
    }
    // let simulation to have the access of the updating each event's place in time
    public double getTime(){
        return time;
    }
     // let simulation to have the access of the updating the customer(name/label)
    public Customer getCustomer(){
        return customer;        
    }
    //let simulation to have the access of the adding the event type
    public int getType(){
        return eventType;
    }
    
    // here, we compare each event and rank order based on the time when each events are placed in the priority queue
    public int compareTo(EventsSetup other){
        // if the new put in event's time is later than the original exsiting one
        if(time>other.time){
            return 1;
        } // if the new put in event's time is equal to the original exsiting one
        else if (time==other.time){
            return 0;
        } // if the new put in event's time is prior to the  original exsiting one        
        else{
            return -1;
        }
    }
}
