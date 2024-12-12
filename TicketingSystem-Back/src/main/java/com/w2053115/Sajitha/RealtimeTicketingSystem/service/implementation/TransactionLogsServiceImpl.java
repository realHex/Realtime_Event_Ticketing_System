package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response.TransactionLogsDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.TransactionLogRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.TransactionLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementation of the TransactionLogsService interface.
 * Manages transaction logs by saving logs to the database and handling real-time logs.
 */
@Service
public class TransactionLogsServiceImpl implements TransactionLogsService {

    @Autowired
    TransactionLogRepo transactionLogRepo; // Repository for saving transaction logs to the database.

    // In-memory storage for real-time transaction logs.
    private final ArrayList<TransactionLogsDTO> savedLogs = new ArrayList<>();

    /**
     * Adds a transaction log entry to the database.
     *
     * @param actor       The actor performing the action (e.g., customer or vendor).
     * @param action      The action performed (e.g., "purchase" or "add").
     * @param noOfTickets The number of tickets involved in the transaction.
     */
    @Override
    public void addDatabase(String actor, String action, int noOfTickets) {
        TransactionLogs transactionLog = new TransactionLogs(actor, action, noOfTickets);
        transactionLogRepo.save(transactionLog); // Save the log to the database using the repository.
    }

    /**
     * Adds a real-time transaction log entry to an in-memory list.
     *
     * @param actor      The actor performing the action.
     * @param action     The action performed.
     * @param ticketPool The current size of the ticket pool after the action.
     */
    public void addRealtime(String actor, String action, int ticketPool) {
        TransactionLogsDTO transactionLogsDTO = new TransactionLogsDTO(actor, action, ticketPool);
        savedLogs.add(transactionLogsDTO); // Add the log object to the List
    }

    /**
     * Retrieves the list of real-time transaction logs.
     *
     * @return A list of transaction logs stored in memory.
     */
    @Override
    public ArrayList<TransactionLogsDTO> getLogTransactions() {
        return savedLogs;
    }

    /**
     * Clears all real-time transaction logs from memory.
     */
    @Override
    public void resetLogs() {
        savedLogs.clear();
    }
}
