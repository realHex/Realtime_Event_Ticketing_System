package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a vendor in the system who adds tickets to the ticket pool.
 * A vendor has attributes like the number of tickets to release, the interval for releasing tickets,
 * and contact information.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    @Id
    private String vendorId; //Automatically generated an objectId by MongoDB
    private int ticketsPerRelease;
    private int releaseInterval;

    // Simulating real world date, in case needed to add
    private String name;
    private String contactNo;
    private LocalDateTime created;

    /**
     * Constructor to create a Vendor with specific details.
     *
     * @param ticketsPerRelease The number of tickets the vendor releases at a time.
     * @param releaseInterval   The interval (in seconds) at which the vendor releases tickets.
     * @param name              The name of the vendor.
     * @param contactNo         The contact number of the vendor.
     */
    public Vendor(int ticketsPerRelease, int releaseInterval, String name, String contactNo) {
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.name = name;
        this.contactNo = contactNo;
        this.created = LocalDateTime.now();
    }
}
