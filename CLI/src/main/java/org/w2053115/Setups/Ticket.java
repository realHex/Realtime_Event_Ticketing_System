package org.w2053115.Setups;

public class Ticket {
    private int ticketId;
    private String ticketName;
    private String type;
    private long price;

    public Ticket(int ticketId, String ticketName,String type, long price) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.type = type;
        this.price = price;
    }
}
