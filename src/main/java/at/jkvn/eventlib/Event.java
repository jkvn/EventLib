package at.jkvn.eventlib;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Event {
    private boolean cancelled = false;

    public void cancel() {
        this.cancelled = true;
    }

    public void uncancel() {
        this.cancelled = false;
    }
}