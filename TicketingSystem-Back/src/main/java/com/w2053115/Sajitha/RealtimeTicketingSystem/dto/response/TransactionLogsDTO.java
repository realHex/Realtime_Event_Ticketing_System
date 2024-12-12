package com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Data Transfer Object (DTO) for transaction logs, used to send log data to the frontend.
 * This class contains the log details including the time of the transaction, the actor (who performed the action),
 * the action performed, and the current ticket pool size.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionLogsDTO {

    // Sending these parameters to the frontend to display logs
    private String time;
    private String actor;
    private String action;
    private int ticketPoolSize;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * Constructor to create a TransactionLogsDTO with specific details.
     * The time is set to the current time when the log entry is created.
     *
     * @param actor        The actor who performed the action.
     * @param action       The action performed.
     * @param ticketPoolSize The current size of the ticket pool.
     */
    public TransactionLogsDTO(String actor, String action, int ticketPoolSize) {
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.time = currentTime.format(formatter);

        this.actor = actor;
        this.action = action;
        this.ticketPoolSize = ticketPoolSize;
    }
}
