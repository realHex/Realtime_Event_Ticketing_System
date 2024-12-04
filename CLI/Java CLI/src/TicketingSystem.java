import Setups.Configuration;
import Setups.TicketPool;
import Vendor.Vendor;
import java.util.ArrayList;
import Customer.Customer;
import Setups.Log;

public class TicketingSystem {
    private static boolean start = false;
    private static final ArrayList<Vendor> vendorObjectList = new ArrayList<Vendor>();
    private static final ArrayList<Thread> vendorThreadList = new ArrayList<Thread>();
    private static final ArrayList<Customer> customerObjectList = new ArrayList<Customer>();
    private static final ArrayList<Thread> customerThreadList = new ArrayList<Thread>();


    public static void main (String[] args) {
        //Configuration.Configuration.initiateParameters();
        Configuration.initiateParameters();
        TicketPool.makingInitialTicketPool();
        Log.createLogFile();


        Vendor vendor1 = new Vendor(10,5);
        Vendor vendor2 = new Vendor(10,5);


        Customer customer1 = new Customer(4,5,1);
        Customer customer2 = new Customer(4,5,1);

        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);
        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);

        vendorThread1.start();
        customerThread1.start();
        vendorThread2.start();
        customerThread2.start();


        //



    }

    public void startApplication() {
        start = true;
        for (int i = 0; i <= vendorThreadList.size(); i++) {
            if (!vendorThreadList.get(i).isAlive()) {
                vendorThreadList.get(i).start();
            } else if (vendorThreadList.get(i).getState() == Thread.State.WAITING) {
                vendorThreadList.get(i).notify();
            } else {
                Log.log("WARNING", "Vendor Thread " + i + 1 + "is in an unknown state");
            }
        }
        for (int i = 0; i <= customerThreadList.size(); i++) {
            if (!customerThreadList.get(i).isAlive()) {
                customerThreadList.get(i).start();
            } else if (customerThreadList.get(i).getState() == Thread.State.WAITING) {
                customerThreadList.get(i).notify();
            } else {
                Log.log("WARNING", "Customer Thread " + i + 1 + "is in an unknown state");
            }
        }
    }
    public void stopApplication() {
        start = false;
        int i = 0;
        try {
            for (i = 0; i <= vendorThreadList.size(); i++) {
                vendorThreadList.get(i).wait();
            }
        }
        catch (InterruptedException e){
            Log.log("WARNING", "Vendor Thread " + i + 1 + "has been interrupted while waiting");
        }

        try {
            for (i = 0; i <= vendorThreadList.size(); i++) {
                vendorThreadList.get(i).wait();
            }
        }
        catch (InterruptedException e){
            Log.log("WARNING", "Customer Thread " + i + 1 + "has been interrupted while waiting");
        }
    }

    public void addVendor(){
        Vendor vendor = new Vendor();
        vendorCreator(vendor);
    }

    public void addVendorWithOptionalValues(){
        Vendor vendor = new Vendor(); //Missing Parameters
        vendorCreator(vendor);
    }
    public void vendorCreator(Vendor vendor){
        vendorObjectList.add(vendor);
        Thread vendorThread = new Thread(vendor);
        vendorThreadList.add(vendorThread);
        if (start) {
            vendorThread.start();
        }
    }

    public void addCustomer(){
        Customer customer = new Customer();
        customerCreator(customer);
    }

    public void addCustomerWithOptionalValues(){
        Customer customer = new Customer(); //Missing Parameters
        customerCreator(customer);
    }
    public void customerCreator(Customer customer){
        customerObjectList.add(customer);
        Thread customerThread = new Thread(customer);
        customerThreadList.add(customerThread);
        if (start) {
            customerThread.start();
        }
    }

}