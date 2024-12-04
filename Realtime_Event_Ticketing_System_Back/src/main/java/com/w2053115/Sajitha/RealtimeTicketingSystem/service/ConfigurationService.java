package com.w2053115.Sajitha.RealtimeTicketingSystem.service;

import com.w2053115.Sajitha.RealtimeTicketingSystem.dto.request.ConfigurationSaveRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface ConfigurationService {

    String saveConfiguration(ConfigurationSaveRequestDTO saveRequestDTO);
}
