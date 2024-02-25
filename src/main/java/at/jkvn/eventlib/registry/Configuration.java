package at.jkvn.eventlib.registry;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Configuration {
    public String[] allowedHosts;
    public ListenerRegistryType type;
}