package com.w2053115.Sajitha.RealtimeTicketingSystem.repository;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Customer;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionLogRepo extends MongoRepository<TransactionLogs,String> {

}

