package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document //Indication to springboot that this is the table name in MongoDB Compass
@Data //Lombok, used for getters and setters
public class ExampleVendorModel {
    @Id //Marks the primary key
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    public ExampleVendorModel(int ticketsPerRelease, int releaseInterval) {
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    public ExampleVendorModel() {
    }

}


