package com.w2053115.Sajitha.RealtimeTicketingSystem.repository;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing vendor data in MongoDB.
 * This interface provides methods for interacting with vendor records, including finding the most recently created vendor.
 */
@Repository
public interface VendorRepo extends MongoRepository<Vendor, String> {

    /**
     * Finds the most recently created vendor.
     *
     * @return the most recent vendor.
     */
    Vendor findFirstByOrderByCreatedDesc();
}
