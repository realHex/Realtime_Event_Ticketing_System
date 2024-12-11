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
public class Customer {
    @Id
    private String customerId;
    private int ticketsPerRetrieval;
    private int releaseInterval;

    private String name;
    private String contactNo;
    private boolean priority;
    private LocalDateTime created;

    public Customer(int ticketsPerRetrieval, int releaseInterval, String name, String contactNo, boolean priority) {
        this.ticketsPerRetrieval = ticketsPerRetrieval;
        this.releaseInterval = releaseInterval;
        this.name = name;
        this.contactNo = contactNo;
        this.priority = priority;
        this.created = LocalDateTime.now();
    }
}
