import at.jkvn.eventlib.EventLib;
import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.registry.Configuration;
import at.jkvn.eventlib.registry.ListenerRegistryType;
import events.StartupEvent;

public class TestProject {
    public static void main(String[] args) {
        EventLib.configure(Configuration.builder()
                .allowedHosts(new String[]{})
                .type(ListenerRegistryType.AUTOMATIC)
                .build());

        EventLib.call(new StartupEvent());
    }
}
