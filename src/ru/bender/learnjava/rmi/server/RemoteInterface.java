package ru.bender.learnjava.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by bender on 23.10.16.
 */
public interface RemoteInterface extends Remote {
    public String sayHello() throws RemoteException;
}
