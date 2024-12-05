package com.w2053115.Sajitha.RealtimeTicketingSystem.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document //Indication to springboot that this is the document name in MongoDB Compass
@Data //Lombok, used for getters and setters
@AllArgsConstructor
public class ExampleConfiguration {

    @Id
    private int configurationId;

    @NonNull //To indicate that the value cannot be null (no runtime validation, solely for documentation)
    private int totalTickets;

    @NonNull
    private int ticketReleaseRate;

    @NonNull
    private int customerRetrievalRate;

    @NonNull
    private int maxTicketCapacity;
}
