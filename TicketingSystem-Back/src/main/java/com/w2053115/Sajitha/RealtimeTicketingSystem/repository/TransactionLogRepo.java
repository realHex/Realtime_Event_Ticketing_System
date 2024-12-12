package com.w2053115.Sajitha.RealtimeTicketingSystem.repository;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.TransactionLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for accessing and managing transaction log data in MongoDB.
 * This interface provides methods for interacting with transaction logs.
 */
public interface TransactionLogRepo extends MongoRepository<TransactionLogs, String> {
}
