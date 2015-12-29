package com.dev.tim.shopper_data.event;

/**
 * Created by Tim on 12/28/2015.
 */
public class FailureEvent {
    private String message;

    public FailureEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
