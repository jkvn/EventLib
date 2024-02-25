import at.jkvn.eventlib.EventLib;
import at.jkvn.eventlib.Listener;
import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.registry.Configuration;
import at.jkvn.eventlib.registry.ListenerRegistryType;
import events.StartupEvent;
import listener.StartupListener;

public class TestProject {
    public static void main(String[] args) {
        EventLib.configure(Configuration.builder()
                .allowedHosts(new String[]{})
                .type(ListenerRegistryType.MANUAL)
                .build());

        EventLib.registerListener(new StartupListener());
        System.out.println("Hello, World!");
        EventLib.call(new StartupEvent());
    }

    @EventHandler
    public void onStartup(StartupEvent event) {
        System.out.println("Startup event called at ms");
    }
}
