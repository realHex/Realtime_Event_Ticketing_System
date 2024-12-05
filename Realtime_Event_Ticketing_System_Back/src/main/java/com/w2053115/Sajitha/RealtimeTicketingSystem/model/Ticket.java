package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private int ticketId;
    private String ticketName;
    private String type;
    private long price;
}
