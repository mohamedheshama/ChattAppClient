package org.project.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnectionController {
    private Registry registry;
    private int port;
    ServicesInterface servicesInterface;

    public ServicesInterface getServicesInterface() {
        return servicesInterface;
    }

    // toDo define chat seerver interface
    public ServerConnectionController(String ip, int port) throws RemoteException, NotBoundException {
        this.port = port;
        registry = LocateRegistry.getRegistry("localhost", 1260);
        servicesInterface = ((ServicesInterface) registry.lookup("ServerServices"));
    }

    public Registry getRegistry() {
        return registry;
    }

    public int getPort() {
        return port;
    }
}
