package org.project.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnectionController {
    private Registry registry;
    private int port;
    ServicesInterface servicesInterface;

    // toDo define chat seerver interface
    public ServerConnectionController(String ip, int port) throws RemoteException, NotBoundException {
        this.port = port;
        registry = LocateRegistry.getRegistry(ip, port);
        servicesInterface = ((ServicesInterface) registry.lookup(""));

    }

    public Registry getRegistry() {
        return registry;
    }

    public int getPort() {
        return port;
    }
}
