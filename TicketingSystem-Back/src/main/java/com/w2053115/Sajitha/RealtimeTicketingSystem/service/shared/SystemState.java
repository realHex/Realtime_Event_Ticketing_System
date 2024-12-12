package com.w2053115.Sajitha.RealtimeTicketingSystem.service.shared;

import lombok.Getter;
import lombok.Setter;

/**
 * Enum representing the different states of the system.
 * It defines three possible states: RUNNING, PAUSED, and STOPPED.
 * The static state variable holds the current system state, which can be accessed or modified.
 */
public enum SystemState {
    RUNNING,
    PAUSED,
    STOPPED;

    @Getter
    @Setter
    public static SystemState state = STOPPED;

}
