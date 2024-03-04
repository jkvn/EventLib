package at.jkvn.eventlib.registry;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Builder
@Getter
@Setter
public class Configuration {
    private InetSocketAddress[] allowedHosts;
    private ListenerRegistryType type;
    private int port;
}