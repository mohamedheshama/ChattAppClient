package org.project.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnectionController {
    private Registry registry;
    private int port;

    // toDo define chat seerver interface
    public ServerConnectionController(String ip, int port) throws RemoteException {
        registry = LocateRegistry.getRegistry(ip, port);
        //todo lookup registry
        this.port = port;
    }

    public Registry getRegistry() {
        return registry;
    }

    public int getPort() {
        return port;
    }
}
