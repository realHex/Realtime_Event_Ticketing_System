package org.w2053115.Setups;

import org.w2053115.Setups.SharedTicketPool.TicketPool;

/**
 * This abstract class provides a common base for ticket handlers, such as customers and vendors.
 *
 * It encapsulates the shared `TicketPool` object, allowing derived classes to interact with it.
 */
public abstract class AbstractTicketHandler {
    protected TicketPool ticketPool;

    /**
     * Constructs a new AbstractTicketHandler object.
     *
     * @param ticketPool The shared ticket pool used by the handler.
     */
    public AbstractTicketHandler(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }
}