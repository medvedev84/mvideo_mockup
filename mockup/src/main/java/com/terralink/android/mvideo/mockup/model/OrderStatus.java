package com.terralink.android.mvideo.mockup.model;

public enum OrderStatus {
    /**
     * Target states: Paid, Cancelled
     */
    Open,
    /**
     * Target states: Logged
     */
    Paid,
    /**
     * Final state
     */
    Cancelled,
    /**
     * Final state
     */
    Complete
}
