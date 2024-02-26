package event;

import at.jkvn.eventlib.Event;

public class DummyEvent extends Event {
    private boolean wasCalled = false;

    public void call() {
        wasCalled = true;
    }

    public boolean wasCalled() {
        return wasCalled;
    }
}
