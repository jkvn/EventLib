import at.jkvn.eventlib.EventLib;
import at.jkvn.eventlib.Listener;
import at.jkvn.eventlib.annotation.EventHandler;
import at.jkvn.eventlib.registry.Configuration;
import at.jkvn.eventlib.registry.ListenerRegistryType;
import event.DummyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject implements Listener {

    @BeforeAll
    public static void setUp() {
        EventLib.configure(Configuration.builder()
                .type(ListenerRegistryType.AUTOMATIC)
                .build());
    }

    @Test
    public void configure() {
        assertEquals(ListenerRegistryType.AUTOMATIC, EventLib.getConfiguration().getType());
    }

    @Test
    public void unAndRegisterListener() throws Exception {
        EventLib.getConfiguration().setType(ListenerRegistryType.MANUAL);

        EventLib.registerListener(this);
        assertEquals(1, EventLib.getListeners().size());
        EventLib.unregisterListener(this);
        assertEquals(0, EventLib.getListeners().size());

        EventLib.getConfiguration().setType(ListenerRegistryType.AUTOMATIC);
    }

    @Test
    public void registerListenerOnAutomatic() {
        assertThrows(Exception.class, () -> {
            EventLib.registerListener(this);
        });
    }

    @Test
    public void call() {
        DummyEvent dummyEvent = new DummyEvent();

        EventLib.call(dummyEvent);

        assertTrue(dummyEvent.wasCalled());
    }

    @EventHandler
    public void onDummyEvent(DummyEvent event) {
        event.call();
    }
}
