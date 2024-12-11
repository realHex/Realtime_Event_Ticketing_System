package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response.TransactionLogsDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.TransactionLogRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.TransactionLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TransactionLogsServiceImpl implements TransactionLogsService {

    @Autowired
    TransactionLogRepo transactionLogRepo;
    private final ArrayList<TransactionLogsDTO> savedLogs = new ArrayList<>();

    @Override
    public void addDatabase(String actor, String action, int noOfTickets) {
        TransactionLogs transactionLog = new TransactionLogs(actor,action,noOfTickets);
        transactionLogRepo.save(transactionLog);
    }

    public void addRealtime(String actor, String action, int ticketPool) {
        TransactionLogsDTO transactionLogsDTO = new TransactionLogsDTO(actor, action, ticketPool);
        savedLogs.add(transactionLogsDTO);
    }

    @Override
    public ArrayList<TransactionLogsDTO> getLogTransactions() {
        return savedLogs;
    }

    @Override
    public void resetLogs() {
        savedLogs.clear();
    }
}
