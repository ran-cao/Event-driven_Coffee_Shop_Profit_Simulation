import java.io.*;
import java.util.Scanner;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.*; 

public class ExperimentalController
{
    FileWriter writer; // imput the file writer
    double profit;
    double cost;
    double serviceTime;
    int cashierCount = 4;
    List<Double> arrivalTime = new ArrayList<Double>();
    public static void main(String[] args) {
        ExperimentalController r = new ExperimentalController();
        r.run();     
    }    

    public void run(){       
        Scanner sc = null; // initialize the value
        PrintWriter printWriter = null; // initialize the value        
        try {
            sc = new Scanner (new FileReader("input-2.txt")); //create a scanner to scan the input file 
            // create a new file, serving as recording the outputs
            File outputFile = new File("test.txt"); 
            // print out the outputs, which is saved in the 'outputFile'
            writer = new FileWriter(outputFile);
            // create an arraylist to store all the strings
            ArrayList<String> eachLine = new ArrayList<String>();
            while(sc.hasNextLine()){             
              eachLine.add(sc.nextLine());            
            }      
            
             // the first element in the arraylist is the profit 
             double profit = Double.parseDouble(eachLine.get(0));
             // the second element in the arraylist is the cost    
             double cost = Double.parseDouble(eachLine.get(1));
             // the third element in the arraylist is the service time
             double serviceTime = Double.parseDouble(eachLine.get(2));
             //start from the fourth line, the remaining lines are customers' arriving time            
            for(int i=3;i<eachLine.size();i++){
                //first delete AM/PM in the text
                String [] deletedVersion = new String [eachLine.size()];
                deletedVersion[i]=eachLine.get(i);
                deletedVersion[i] = deletedVersion[i].substring(0, 8);
                String [] eachNumber = deletedVersion[i].split(":");
                double hour=Integer.parseInt(eachNumber[0]);
                double minute=Integer.parseInt(eachNumber[1]);                
                double second=Integer.parseInt(eachNumber[2]);
                arrivalTime.add(i-3,(second + (60 * minute) + (3600 * (hour-6))));
                
             }
            
            CoffeeShopSimulation simulation = new CoffeeShopSimulation(cashierCount, profit, cost, serviceTime, arrivalTime, writer );
            simulation.run();
            writer.write("The amount of daily net profit is : " + simulation.getProfit() + "\n");
            writer.write("The rate of overflow is : " + simulation.getRateOfOverFlow());
            writer.write("The average waiting time of all customers served is : " + simulation.averageWaitingTime() + "\n");
            writer.write("The maximum waiting time of all customers served is : " + simulation.maximumWaitingTime() + "\n");
            writer.write("The total number of customers is: " + simulation.getTotalCustomers() + "\n");
            writer.flush();
            writer.close();
        } 
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}