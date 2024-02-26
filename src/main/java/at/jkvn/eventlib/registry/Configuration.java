package at.jkvn.eventlib.registry;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Configuration {
    private String[] allowedHosts;
    private ListenerRegistryType type;
}