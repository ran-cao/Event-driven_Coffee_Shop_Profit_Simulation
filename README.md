# Event-driven Coffee Shop Profit Simulation

Design an optimal delivery routes for trucks using a revised version of Dijkstra’s algorithm and Graph Theory

### Project Glimpse
This project is a Coffee Shop event-driven simulation process. By scanning an input file, which contains a list of given perimeters (cost per cashier, profit per customer, and service time of each cashier), we were able to find daily net profit, rate of overflow, average waiting time, and maximum waiting time, with a fixed number of cashier. Then we altered the number of cashier to find a profit maximization point.

Here we assume that, as the number of cashier increases, the coffee shop could gain more profits. As the cashier number reaches one point, the coffee shop would generate a maximum profit. However, if the cashier number exceeds that equilibrium point, the profits start to diminish.

### Goal & Data Structure
The goal is to work with different data structure to store data information for implementation, for example, ArrayList, LinkedList, Queue, and PriorityQueue were used in coding. It’s an event-driven simulation. The whole simulation is processed through the arrival and departure events in time order.

### (1) Classes Design

`CoffeeShopSimulation.java` class is the simulation class contains all the methods for implementation. By checking whether the type is arrival or departure, there are corresponding implementations related to different types. This class got perimeters information (arriving time, cost, profit, and service time) from the experimental controller class. Whenever we changed the value of a variable in the controller’s class, all the implemented methods were in this class and this class returned the final result to the experimental controller class, and then printed out in a text file. If an arrival happened, we check if the number of customer exceeds the shop’s capacity, by limiting the line’s length smaller than 8*number of cashier. If overflow did not occur, we add this customer into the waiting list. Otherwise, this customer has to leave. In the final process, we can count the number of being served customers to calculate the profit, which is the goal for this simulation.

`Customer.java`: two variables (arrivalTime and a served cashier’s id) were passed from the controller’s class to this class, to distinguish each customer. All the methods in this class are about the related information of a single customer: arrival time, waiting time, service time and corresponding cashier’s id. 

`Cashier.java` class stores all the related information of a single cashier. Each of the customers is marked with a service time (this parameter is passed from the experimental controller class) and the cashier’s status (if the cashier is available or not). 

`EventsSetup.java` class implements the comparable interface because we need to compare every two events and rank them in order of time. An event is listed ahead of another if its processing time is earlier. Each event contains: which customer is leaving or arrived, what is the time for his/her to start this event, and what is the type of this event (arrival or departure).

`ExperimentController.java` runs the whole program and calculates the profits, overflow rate, average waiting time, and maximum waiting time. The controller class control the whole program. We change the value of variable here and try to get the number back from the simulation class. Each time, by changing number of cashier, we are able to get different results.

### (2) Choice of algorithm
When we calculated the time used, it is related to the choice of algorithm. For this whole program, even though various data structures were chosen, for most part, the algorithm is O(1), for example, when adding customers in the ArrayList, or removing the first customer in the LinkedList. However, if a for-loop is used, for instance, to get all customers’ waiting time, the algorithm is O(n).

### (3) Data Structure
We used priority queue to store the event and execute each events by the time when an event, either Arrival or Departure, is added into the priority queue.
We used linkedlist(queue) for customers who are waiting in the line who have not been served yet because it is easier to remove the first node of the linkedlist and process it.
The choice of using ArrayList to store all the customers can be beneficial when we added a new customer into the ArrayList since arraylist do not have any space limitation, which means that we do not need to initialize the size of the customer list because the size the unknown at first. Also when we want to locate to a certain customer, it is efficient to access with the ArrayList since “.get()” needs constant time.

### Results
In the beginning, when one more cashier was hired, the total profit would go up, because this additional cashier could serve more customers by decreasing the number of overflow. However, when the number of cashier was too many that the marginal cost exceeded the marginal revenue, profits started to decline due to the cost of salary for all cashiers, since there were only limited customers served by too many cashiers.
Different type of data structure enables us to store data and implement methods more effectively: PriorityQueue can always track the first event needed to be processed. LinkedList implements Queue can delete the first element efficiently and therefore the waiting line is stored in a linkedList. ArrayList did well in get a specific element and all the customers were stored in an arrayList.

