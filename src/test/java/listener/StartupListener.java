package listener;

import at.jkvn.eventlib.Listener;
import at.jkvn.eventlib.annotation.EventHandler;
import events.StartupEvent;


public class StartupListener implements Listener {

    public void onStartUpEvent(StartupEvent event) {
        System.out.println("Listener Start up time: " + (event.getStartTimestamp() - System.currentTimeMillis()) + "ms");
    }
}
