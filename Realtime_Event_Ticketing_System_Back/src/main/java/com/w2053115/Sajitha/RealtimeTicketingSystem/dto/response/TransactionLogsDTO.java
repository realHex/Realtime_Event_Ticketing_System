package com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionLogsDTO {

    private String time;
    private String actor;
    private String action;
    private int ticketPoolSize;


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public TransactionLogsDTO(String actor, String action, int ticketPoolSize) {
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.time = currentTime.format(formatter);

        this.actor = actor;
        this.action = action;
        this.ticketPoolSize = ticketPoolSize;
    }
}
