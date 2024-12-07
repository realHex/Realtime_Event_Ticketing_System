package org.w2053115.Setups.SharedTicketPool;

public interface TicketPoolOperations {

    void addTicket(int vendorId, int noOfTickets);

    void removeTicket(int customerId, int noOfTickets);

    //Adding tickets to the ticketpool before the threads start
    void ticketPoolPopulator();
}
