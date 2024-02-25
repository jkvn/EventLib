package events;

import at.jkvn.eventlib.Event;

public class StartupEvent extends Event {

    public StartupEvent(long startTimestamp) {
        super(startTimestamp);
    }
}
