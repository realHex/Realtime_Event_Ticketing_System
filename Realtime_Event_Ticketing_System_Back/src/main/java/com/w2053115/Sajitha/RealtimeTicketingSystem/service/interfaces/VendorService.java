package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface VendorService {
    void createVendor();
    void removeVendor();
    void startVendors();
    void stopVendors();
    void resumeVendors();
    void resetVendors();
    int getVendors();
}
