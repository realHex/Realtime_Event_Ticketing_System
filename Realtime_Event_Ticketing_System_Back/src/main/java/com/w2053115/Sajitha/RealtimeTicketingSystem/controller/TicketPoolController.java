package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ticketpool")
public class TicketPoolController {

    @Autowired
    private TicketPool ticketPool;

    @GetMapping(path = "/total-tickets")
    public int getTotalTickets() {
        return ticketPool.getTotalTickets();
    }

    @GetMapping(path = "/added-tickets")
    public int getAddedTickets() {
        return ticketPool.getTotalAddedTickets();
    }

    @GetMapping(path = "/purchased-tickets")
    public int getPurchasedTickets() {
        return ticketPool.getTotalPurchasedTickets();
    }

    @GetMapping(path = "/max-ticket-capacity")
    public int getMaxTicketCapacity() {
        return ticketPool.getMaxTicketCapacity();
    }
}
