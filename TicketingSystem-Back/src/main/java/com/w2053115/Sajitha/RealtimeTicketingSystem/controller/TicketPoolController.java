package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling HTTP requests related to the ticket pool.
 * It provides endpoints for retrieving information about the ticket pool such as total tickets,
 * added tickets, purchased tickets, and the maximum ticket capacity.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
@RequestMapping("api/ticketpool")
public class TicketPoolController {

    @Autowired
    private TicketPool ticketPool;

    /**
     * Endpoint to retrieve the total number of tickets available in the pool.
     *
     * @return The total number of tickets in the pool.
     */
    @GetMapping(path = "/total-tickets")
    public int getTotalTickets() {
        return ticketPool.getTotalTickets();
    }

    /**
     * Endpoint to retrieve the total number of tickets added to the pool.
     *
     * @return The total number of tickets that have been added to the pool.
     */
    @GetMapping(path = "/added-tickets")
    public int getAddedTickets() {
        return ticketPool.getTotalAddedTickets();
    }

    /**
     * Endpoint to retrieve the total number of tickets that have been purchased.
     *
     * @return The total number of purchased tickets.
     */
    @GetMapping(path = "/purchased-tickets")
    public int getPurchasedTickets() {
        return ticketPool.getTotalPurchasedTickets();
    }

    /**
     * Endpoint to retrieve the maximum capacity of the ticket pool.
     *
     * @return The maximum ticket capacity of the pool.
     */
    @GetMapping(path = "/max-ticket-capacity")
    public int getMaxTicketCapacity() {
        return ticketPool.getMaxTicketCapacity();
    }
}
