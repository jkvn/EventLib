package listener;

import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.annotation.Priority;
import at.jkvn.eventlib.enumeration.EventPriority;
import events.StartupEvent;


public class StartupListener {

    @EventHandler
    @Priority(EventPriority.HIGHEST)
    public void onStartUpEvent(StartupEvent event) {
        System.out.println("Highest priority event!");
    }

    @EventHandler
    @Priority(EventPriority.HIGH)
    public void onStartUpEventHigh(StartupEvent event) {
        System.out.println("High priority event!");
    }

    @EventHandler
    @Priority(EventPriority.NORMAL)
    public void onStartUpEventNormal(StartupEvent event) {
        System.out.println("Normal priority event!");
    }

    @EventHandler
    @Priority(EventPriority.LOWEST)
    public void onStartUpEventLowest(StartupEvent event) {
        System.out.println("Lowest priority event!");
    }

    @EventHandler
    @Priority(EventPriority.LOW)
    public void onStartUpEventLow(StartupEvent event) {
        System.out.println("Low priority event!");
    }
}
