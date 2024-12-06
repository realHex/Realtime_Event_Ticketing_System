package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface VendorService {
    String createVendor();
    String removeVendor();
    void startVendors();
    void stopVendors();
    void resumeVendors();
    void resetVendors();
    int getVendors();
}
