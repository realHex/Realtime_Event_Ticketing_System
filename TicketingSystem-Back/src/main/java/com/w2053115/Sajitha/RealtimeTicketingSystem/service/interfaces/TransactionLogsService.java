package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response.TransactionLogsDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Interface for managing transaction logs in the ticketing system.
 * It provides methods for adding logs to the database and real-time logs, retrieving logs,
 * and resetting the logs.
 */
@Service
public interface TransactionLogsService {

    /**
     * Adds a log entry to the database for a specific action performed by an actor.
     *
     * @param actor the entity performing the action (e.g., vendor or customer)
     * @param action the action being performed (e.g., adding or purchasing tickets)
     * @param noOfTickets the number of tickets involved in the transaction
     */
    void addDatabase(String actor, String action, int noOfTickets);

    /**
     * Adds a real-time log entry for an action performed by an actor.
     *
     * @param actor the entity performing the action (e.g., vendor or customer)
     * @param action the action being performed (e.g., adding or purchasing tickets)
     * @param ticketPool the current state of the ticket pool after the action
     */
    void addRealtime(String actor, String action, int ticketPool);

    /**
     * Retrieves a list of transaction logs.
     *
     * @return a list of transaction log data transfer objects (DTOs)
     */
    ArrayList<TransactionLogsDTO> getLogTransactions();

    /**
     * Resets the transaction logs, clearing all stored log entries.
     */
    void resetLogs();
}
