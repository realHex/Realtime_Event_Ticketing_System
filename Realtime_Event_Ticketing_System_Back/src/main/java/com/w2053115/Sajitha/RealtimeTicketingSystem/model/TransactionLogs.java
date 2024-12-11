package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLogs {
    @Id
    private String logId; //Automatically generated an objectId by MongoDB

    private String actor;
    private String action;
    private int noOfTickets;

    private LocalDateTime time;

    public TransactionLogs(String actor, String action, int noOfTickets) {
        this.actor = actor;
        this.action = action;
        this.noOfTickets = noOfTickets;
        this.time = LocalDateTime.now();
    }
}
