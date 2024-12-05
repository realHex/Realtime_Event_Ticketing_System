package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    //Simulating real world date, in case needed to add
    private String name;
    private String contactNo;

    public Vendor(int ticketsPerRelease, int releaseInterval, String name, String contactNo) {
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.name = name;
        this.contactNo = contactNo;
    }
}
