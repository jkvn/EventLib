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

    private static EventLib eventLib;

    @BeforeAll
    public static void setUp() {
        eventLib = new EventLib();
        eventLib.init(Configuration.builder()
                .type(ListenerRegistryType.AUTOMATIC)
                .build());
    }

    @Test
    public void configure() {
        assertEquals(ListenerRegistryType.AUTOMATIC, eventLib.getConfiguration().getType());
    }

    @Test
    public void unAndRegisterListener() throws Exception {
        eventLib.getConfiguration().setType(ListenerRegistryType.MANUAL);

        eventLib.registerListener(this);
        assertEquals(1, eventLib.getListeners().size());
        eventLib.unregisterListener(this);
        assertEquals(0, eventLib.getListeners().size());

        eventLib.getConfiguration().setType(ListenerRegistryType.AUTOMATIC);
    }

    @Test
    public void registerListenerOnAutomatic() {
        assertThrows(Exception.class, () -> {
            eventLib.registerListener(this);
        });
    }

    @Test
    public void call() {
        DummyEvent dummyEvent = new DummyEvent();

        eventLib.call(dummyEvent);

        assertTrue(dummyEvent.wasCalled());
    }

    @EventHandler
    public void onDummyEvent(DummyEvent event) {
        event.call();
    }
}