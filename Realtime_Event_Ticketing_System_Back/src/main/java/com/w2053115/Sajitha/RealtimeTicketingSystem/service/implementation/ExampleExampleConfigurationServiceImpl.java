package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ExampleConfigurationSaveRequestDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.ExampleConfiguration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repository.ExampleConfigurationRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces.ExampleConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleExampleConfigurationServiceImpl implements ExampleConfigurationService {

    @Autowired
    private ExampleConfigurationRepo exampleConfigurationRepo;
    @Override
    public String saveConfiguration(ExampleConfigurationSaveRequestDTO saveRequestDTO) {
        ExampleConfiguration exampleConfiguration = new ExampleConfiguration(
                1, //since there only needs to be one config file
                saveRequestDTO.getTotalTickets(),
                saveRequestDTO.getTicketReleaseRate(),
                saveRequestDTO.getCustomerRetrievalRate(),
                saveRequestDTO.getMaxTicketCapacity()
        );

        exampleConfigurationRepo.save(exampleConfiguration);
        return "Configuration Saved";
    }
}
