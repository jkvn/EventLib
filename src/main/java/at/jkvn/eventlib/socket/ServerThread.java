package at.jkvn.eventlib.socket;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ServerThread extends Thread {
    private ServerSocket socket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<>();

    public ServerThread(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    @Override
    public void run() {

    }
}