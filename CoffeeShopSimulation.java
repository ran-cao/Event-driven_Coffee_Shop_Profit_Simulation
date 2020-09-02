import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.*;
import java.io.*;
public class CoffeeShopSimulation
{
    // instance variables 
    private PriorityQueue<EventsSetup> eventQueue = new PriorityQueue<EventsSetup>(); // create a priority queue to store the pending event
    private Queue<Customer> list = new LinkedList<Customer>(); // create a linkedlist to store the customer have not be served(waiting in line)
    private ArrayList<Customer> array = new ArrayList<Customer>(); // create an arraylist to store  the customers who are not turned away 
    private Cashier[] cashiers; // the list of all the cashiers
    private int arrivalHappened = 0; // counting the number of arrival events happened, for later calculating the waiting time 
    private int NumOfNoWaiting = 0; // initially, the number of people who don't need to wait is 0
    private double openingTime = (22-6)*3600; // openning time is from 6am to 22pm, in second
    private double cost; // the cost of employing a cashier
    private double profit; // The estimated profit of serving each customer 
    private double netProfit;
    private int overFlow = 0; // initialized the value of overflow
    private double serviceTime;
    private List<Double> arrivalTime;
    private FileWriter writer; // save the implementation in the file

    /**
     * Constructor for objects of class CoffeeShopSimulation
     *  Here, we initialized:the arriving time of customers
     *                       the number of cashier
     *                       the profit to serve each customer
     *                       the cost of each cashier's salary
     *                       the time for serving each customer
     */
    public CoffeeShopSimulation( int cashierCount, double profit, double cost, double serviceTime, List<Double> arrivalTime, FileWriter writer)
    {
        this.serviceTime = serviceTime;
        this.cost = cost;
        this.profit = profit;
        this.writer = writer;
        this.arrivalTime = arrivalTime;
        cashiers = new Cashier[cashierCount];
        setup();
    }

    public void run() throws IOException {         
        EventsSetup e = null;
        // set the current time is zero
        double currentTime = 0;
        // when the priority queue's size is not zero, which means people has started to join the line


               while(eventQueue.size()!=0){
            // delete and process the first event in the priority queue
            e = eventQueue.poll(); 
            writer.write("\n");
            writer.write("current time: " + currentTime + " event type: " + e.getType() + "\n");
            // if the coffee shop is closing           
            // if the event type is arrival
            if(e.getType() == 1) {
                //check if the arrived customer is enable to be served or not because if overflow happened, customer wouldn't wait
                Customer customer = e.getCustomer();
                //writer.write("customer " + customer.getID() + " arrives" + "\n");
                if(list.size()<8*4){
                    array.add(customer);
                    currentTime = customer.getArrivalTime();
                    // when there is no people waiting in the queue and there is available cashier
                    if (list.size() == 0 && checkCashiersAvailable()) {
                        //increase the number of customer who dont have to wait by 1
                        NumOfNoWaiting ++;
                        //customer doesn't have to wait, gets served immediately
                        matchCustomerWithCashier(customer, getAvailableCashier(), currentTime); 
                    }
                    // customer has to wait
                    else {
                        writer.write("customer " + customer.getID() + " is added to waiting queue" + "\n");
                        // add the customer who need to wait in the queue
                        list.add(customer);
                    }
                } 
                else{
                    // if the overflow happens, increase overflow by 1
                    overFlow++; 
                    writer.write("overflow: " + overFlow + "\n");
                    // remove from the total customer arraylist since he/she turns away
                    // array.remove(customer);
                    // update the leaving time for the turned away customer
                    // customer.setLeavingTime(currentTime);
                    // printout the customer information who turned away
                    // writer.write("customer " + customer.getID() + " is turned away" + "\n");
                }
            }   
            else if(e.getType() == 2){ //if the event type is departure
                // the customer who leaves
                Customer leavingCustomer = e.getCustomer();
                // set the leaving customer's leaving time to be the current time
                leavingCustomer.setLeavingTime( currentTime );
                writer.write("customer " + leavingCustomer.getID() + " is leaving at time " + e.getTime() + "\n");
                 // the cashier who just served the leaving customer is free right now
                Cashier cashier = leavingCustomer.getCashier();
                // this cashier can serve next customer
                cashier.finishServing();
                // set the leaving custoner's linked cashier to be null
                leavingCustomer.setCashier(null);
                 // when there are still people waiting in line
                if (list.size() > 0) {
                    // the next customer who gets served 
                    Customer customer = list.remove();
                    // match next customer with the cashier
                    matchCustomerWithCashier(customer, cashier, currentTime);
                }       
                currentTime = leavingCustomer.getArrivalTime();   
            }
        }
    }

        
    
 
    // here we match each customer with the serving cashier
    private void matchCustomerWithCashier(Customer customer, Cashier cashier, double currentTime) throws IOException {
        // update the time when customer get served to be the current time
        customer.setServiceTime(currentTime);
        // when the cashier take the customer, turn the availability to be false
        cashier.takeCustomer();
        // match the customer with correponding cashier 
        customer.setCashier(cashier);
        // the leaving time of the customer to be the current time plus the time for this cashier to serve any customer
        double leavingTime = currentTime + cashier.getServiceTime();
         //create an leaving event for customers, with the placein time to be their leaving time since the type is departure
        EventsSetup leavingEvent = new EventsSetup(customer, leavingTime, 2 );
        //add this leaving event in the priority queue and wait to be executed
        eventQueue.add(leavingEvent);
        writer.write("customer " + customer.getID() + " is served at " + currentTime  + " and will leave at " + leavingTime + "\n");
    }


    // set up all the initials
    private void setup(){
        // initialize the cashiers array
        for (int i = 0 ; i < cashiers.length ; i++) {
            // each cashier's serving time follows exponential distribution
            //double serviceTime = exponentialDistribution();
            cashiers[i] = new Cashier(serviceTime, true);
            try {
                writer.write("service time of cashier " + i + " is " + serviceTime + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //create and add all events
        for(int i=0;i<arrivalTime.size();i++){
            // name some customers to store all the information in arraylist
            Customer currentCustomer = new Customer(arrivalTime.get(i),i);
            // put the customer's information into the events, with arrival type
            EventsSetup event = new EventsSetup(currentCustomer,arrivalTime.get(i), 1);
            // put all events into the priority queue
            eventQueue.add(event);
        }       
    }
    
    // get one available cashier
    private Cashier getAvailableCashier() {
        for ( Cashier c : cashiers ) {
            // if cashier c is available, return this cashier
            if (c.isAvailable()) {
                return c;
            }
        }
        return null;
    }

    // when there are available cashier to serve customer
    private boolean checkCashiersAvailable() {
        return getAvailableCashier() != null;
    }

    // calculate the total waiting time for all customer
    private double totalWaitingTime(){
        double total = 0; // initialize total
        for (int i=0;i<array.size();i++){
            total += array.get(i).getWaitingTime();
        }        
        return total;
    }
    
    // profit = profit of serving each customers - all cashiers' salary   
    public double getProfit(){
        netProfit = profit*array.size() - cost*cashiers.length;
        return netProfit;
    }
    
    // get the number of overflow rate by using the overflow devides the total number of customers
    public double getRateOfOverFlow() throws IOException {
        writer.write("overflow: " + overFlow + "\n");
        return overFlow * 1.0 /array.size();
    }    
    
    // average waiting time is the total waiting time devides the total number of customers
    public double averageWaitingTime(){
        return totalWaitingTime()/(array.size());
    }
    
    // to find the max waiting time among all the customers
    public double maximumWaitingTime(){
        //set the initial
        double maxTime = 0;
        // rejected customers have waitingTime = 0
        for(int i=0; i<array.size();i++){
            // we get all the waiting time
            double waitingTime = array.get(i).getWaitingTime();
            // if next waiting time is larger than the previous one, we update the maxTime
            if (maxTime < waitingTime ) {
                maxTime = waitingTime;
            }
        }
        // return the largest number
        return maxTime;
    }

    // get the number of total customers, which is the size of the arraylist
    public int getTotalCustomers() {
        return array.size();
    }
}
