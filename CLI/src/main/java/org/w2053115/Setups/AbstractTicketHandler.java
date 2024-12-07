package org.w2053115.Setups;

import org.w2053115.Setups.SharedTicketPool.TicketPool;

public abstract class AbstractTicketHandler {
    protected TicketPool ticketPool;
    public AbstractTicketHandler(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }
}
