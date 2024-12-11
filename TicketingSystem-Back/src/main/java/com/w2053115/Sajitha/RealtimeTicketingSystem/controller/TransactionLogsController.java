package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response.TransactionLogsDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.TransactionLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/logs")
public class TransactionLogsController {

    @Autowired
    TransactionLogsService transactionLogsService;

    @GetMapping("/get-logs")
    public ArrayList<TransactionLogsDTO> getTransactionLogs() {
        return transactionLogsService.getLogTransactions();
    }

}
