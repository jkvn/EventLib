package at.jkvn.eventlib;

import lombok.Getter;

/**
 * Abstract class representing an event.
 */
@Getter
public abstract class Event {
    private boolean cancelled = false;

    /**
     * Cancels the event.
     */
    public void cancel() {
        this.cancelled = true;
    }

    /**
     * Uncancels the event.
     */
    public void uncancel() {
        this.cancelled = false;
    }
}