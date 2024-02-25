package at.jkvn.eventlib;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Event {
    private boolean cancelled = false;
}