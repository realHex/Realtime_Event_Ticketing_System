package com.w2053115.Sajitha.RealtimeTicketingSystem.model;


import lombok.*;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Configuration {

    private int totalTickets = 0;
    private int ticketReleaseRate = 0;
    private int customerRetrievalRate = 0;
    private int maxTicketCapacity = 0;

    public void updateConfiguration(Configuration configuration){
        this.totalTickets = configuration.getTotalTickets();
        this.ticketReleaseRate = configuration.getTicketReleaseRate();
        this.customerRetrievalRate = configuration.getCustomerRetrievalRate();
        this.maxTicketCapacity = configuration.getMaxTicketCapacity();
    }




}
