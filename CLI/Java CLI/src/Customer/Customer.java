package Customer;

import Setups.Configuration;
import Setups.Log;
import Setups.TicketPool;

public class Customer implements Runnable{
    private int customerId;
    private int ticketsPerRetrieval;
    private int retrievalInterval;
    private int priority;
    static int customerNo = 1;

    public Customer (int ticketsPerRelease, int releaseInterval, int priority) {
        this.customerId = customerNo;
        this.ticketsPerRetrieval = ticketsPerRelease;
        this.retrievalInterval = releaseInterval;
        this.priority = priority;
        customerNo++;
    }

    public Customer () {
        this.customerId = customerNo;
        this.ticketsPerRetrieval = Configuration.getCustomerRetrievalRate();
        this.retrievalInterval = 1;
        this.priority = 0;
        customerNo++;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TicketPool.removeTicket(customerId, ticketsPerRetrieval);
                Thread.sleep(retrievalInterval * 1000L);
            }
        }
        catch (InterruptedException e){
            Log.log("WARNING", "Vendor " + customerId + " encountered error error in thread.");
        }
    }
}
