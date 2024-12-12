package org.w2053115.Setups;

/**
 * This class represents a ticket, encapsulating its ID, name, type, and price.
 *
 * Tickets are used to represent individual units within the shared ticket pool.
 */
public class Ticket {
    private int ticketId;
    private String ticketName;
    private String type;
    private long price;

    /**
     * Constructs a new Ticket object.
     *
     * @param ticketId The unique identifier of the ticket.
     * @param ticketName The name or description of the ticket.
     * @param type The type of the ticket (e.g., general admission, VIP).
     * @param price The price of the ticket.
     */
    public Ticket(int ticketId, String ticketName, String type, long price) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.type = type;
        this.price = price;
    }
}