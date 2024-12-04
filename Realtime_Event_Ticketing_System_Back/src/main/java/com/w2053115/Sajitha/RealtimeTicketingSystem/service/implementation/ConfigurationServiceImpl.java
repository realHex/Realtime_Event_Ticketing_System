package com.w2053115.Sajitha.RealtimeTicketingSystem.service.implementation;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ConfigurationSaveRequestDTO;
import com.w2053115.Sajitha.RealtimeTicketingSystem.model.Configuration;
import com.w2053115.Sajitha.RealtimeTicketingSystem.repositary.ConfigurationRepo;
import com.w2053115.Sajitha.RealtimeTicketingSystem.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepo configurationRepo;
    @Override
    public String saveConfiguration(ConfigurationSaveRequestDTO saveRequestDTO) {
        Configuration configuration = new Configuration(
                1, //since there only needs to be one config file
                saveRequestDTO.getTotalTickets(),
                saveRequestDTO.getTicketReleaseRate(),
                saveRequestDTO.getCustomerRetrievalRate(),
                saveRequestDTO.getMaxTicketCapacity()
        );

        configurationRepo.save(configuration);
        return "Configuration Saved";
    }
}
