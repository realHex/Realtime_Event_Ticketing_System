package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ExampleConfigurationSaveRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface ExampleConfigurationService {

    String saveConfiguration(ExampleConfigurationSaveRequestDTO saveRequestDTO);
}
