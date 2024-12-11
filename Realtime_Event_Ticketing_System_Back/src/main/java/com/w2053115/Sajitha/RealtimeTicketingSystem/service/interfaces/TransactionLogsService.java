package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response.TransactionLogsDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TransactionLogsService {

    void addDatabase(String actor, String action, int noOfTickets);

    void addRealtime(String actor, String action, int ticketPool);

    ArrayList<TransactionLogsDTO> getLogTransactions();

    void resetLogs();
}
