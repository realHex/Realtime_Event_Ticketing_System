package com.w2053115.Sajitha.RealtimeTicketingSystem.controller;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.response.TransactionLogsDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.TransactionLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller responsible for handling HTTP requests related to transaction logs.
 * It provides an endpoint for retrieving a list of transaction logs.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the Angular frontend
@RequestMapping("api/logs")
public class TransactionLogsController {

    @Autowired
    private TransactionLogsService transactionLogsService;

    /**
     * Endpoint to retrieve a list of transaction logs.
     * This fetches the logs from the service and returns them to the frontend.
     *
     * @return A list of transaction logs in the form of TransactionLogsDTO objects.
     */
    @GetMapping("/get-logs")
    public ArrayList<TransactionLogsDTO> getTransactionLogs() {
        return transactionLogsService.getLogTransactions();
    }
}
