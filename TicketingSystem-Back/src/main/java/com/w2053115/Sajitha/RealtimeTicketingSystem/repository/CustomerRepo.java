package com.w2053115.Sajitha.RealtimeTicketingSystem.repository;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing customer data in MongoDB.
 * Provides methods for querying customer data, including finding customers
 * based on priority status and their creation time.
 */
@Repository
public interface CustomerRepo extends MongoRepository<Customer, String> {

    /**
     * Finds the most recent non-priority customer based on the creation date.
     *
     * @return the most recently created non-priority customer
     */
    Customer findFirstByPriorityFalseOrderByCreatedDesc();

    /**
     * Finds the most recent priority customer based on the creation date.
     *
     * @return the most recently created priority customer
     */
    Customer findFirstByPriorityTrueOrderByCreatedDesc();
}
