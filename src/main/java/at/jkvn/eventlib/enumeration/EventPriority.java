package at.jkvn.eventlib.enumeration;

import lombok.ToString;

/**
 * Enum representing the priority of an event.
 */
@ToString
public enum EventPriority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}