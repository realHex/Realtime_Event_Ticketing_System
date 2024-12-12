package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a customer in the ticketing system.
 * Contains customer details such as tickets per retrieval, release interval, contact information, and priority status.
 * Stores the creation timestamp for the customer instance.
 */
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

    /**
     * Constructs a new Customer with specified ticket retrieval and release details,
     * along with name, contact number, and priority status.
     * Sets the creation timestamp to the current time.
     *
     * @param ticketsPerRetrieval the number of tickets the customer retrieves at a time
     * @param releaseInterval the interval between ticket retrievals in seconds
     * @param name the name of the customer
     * @param contactNo the contact number of the customer
     * @param priority indicates whether the customer has priority status
     */
    public Customer(int ticketsPerRetrieval, int releaseInterval, String name, String contactNo, boolean priority) {
        this.ticketsPerRetrieval = ticketsPerRetrieval;
        this.releaseInterval = releaseInterval;
        this.name = name;
        this.contactNo = contactNo;
        this.priority = priority;
        this.created = LocalDateTime.now();
    }
}
