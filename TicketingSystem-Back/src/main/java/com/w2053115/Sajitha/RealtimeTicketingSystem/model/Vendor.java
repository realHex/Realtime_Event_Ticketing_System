package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Vendor {
    @Id
    private String vendorId; //Automatically generated an objectId by MongoDB
    private int ticketsPerRelease;
    private int releaseInterval;

    //Simulating real world date, in case needed to add
    private String name;
    private String contactNo;
    private LocalDateTime created;

    public Vendor(int ticketsPerRelease, int releaseInterval, String name, String contactNo) {
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.name = name;
        this.contactNo = contactNo;
        this.created = LocalDateTime.now();
    }
}
