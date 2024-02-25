import at.jkvn.eventlib.EventLib;
import at.jkvn.eventlib.Listener;
import at.jkvn.eventlib.annotation.EventHandler;
import events.StartupEvent;

public class NonTestProject implements Listener {

    public NonTestProject() {
        EventLib.registerListener(this);
    }

    public void onStartup(StartupEvent event) {
        System.out.println("Startup evensdadsadsdsdst called at " + (event.getStartTimestamp() - System.currentTimeMillis()) + "ms");
    }
}
