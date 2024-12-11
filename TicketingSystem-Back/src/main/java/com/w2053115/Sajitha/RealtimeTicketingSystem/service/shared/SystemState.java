package com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared;

import lombok.Getter;
import lombok.Setter;

public enum SystemState {
    RUNNING,
    PAUSED,
    STOPPED;

    @Getter
    @Setter
    public static SystemState state = STOPPED;

}
