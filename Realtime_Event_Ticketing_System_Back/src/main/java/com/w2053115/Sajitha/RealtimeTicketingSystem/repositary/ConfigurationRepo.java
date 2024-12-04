package com.w2053115.Sajitha.RealtimeTicketingSystem.repositary;

import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepo extends MongoRepository<Configuration, Integer> {
}
