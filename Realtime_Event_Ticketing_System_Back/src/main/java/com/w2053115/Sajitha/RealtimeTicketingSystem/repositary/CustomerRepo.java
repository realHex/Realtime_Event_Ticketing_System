package com.w2053115.Sajitha.RealtimeTicketingSystem.repositary;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends MongoRepository<Customer, String> {
    Customer findFirstByOrderByCreatedDesc();
}
