package com.w2053115.Sajitha.RealtimeTicketingSystem.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface VendorService {
    public String createVendor();
    public String removeVendor();
    public void startVendors();
    public void stopVendors();
    public void resetVendors();
    public int getVendors();
}
