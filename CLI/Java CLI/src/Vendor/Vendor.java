package Vendor;
import Setups.Configuration;
import Setups.Log;
import Setups.TicketPool;
public class Vendor implements Runnable{
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    static int vendorNo = 1;

    public Vendor (int ticketsPerRelease, int releaseInterval) {
        this.vendorId = vendorNo;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        vendorNo++;
    }

    public Vendor () {
        this.vendorId = vendorNo;
        this.ticketsPerRelease = Configuration.getTicketReleaseRate();
        this.releaseInterval = 1;
        vendorNo++;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TicketPool.addTicket(vendorId, ticketsPerRelease);
                Thread.sleep(releaseInterval * 1000L);
            }
        }
        catch (InterruptedException e){
            Log.log("WARNING", "Vendor " + vendorId + " encountered error error in thread.");
        }
    }
}
