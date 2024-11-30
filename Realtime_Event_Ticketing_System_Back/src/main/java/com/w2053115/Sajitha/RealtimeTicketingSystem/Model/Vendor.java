package com.w2053115.Sajitha.RealtimeTicketingSystem.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document //Indication to springboot that this is the table name in MongoDB Compass
@Data //Lombok, used for getters and setters
public class Vendor {
    @Id //Marks the primary key
    private int vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;

    public Vendor(int ticketsPerRelease, int releaseInterval) {
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
    }

    public Vendor() {
    }

}


