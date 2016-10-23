package ru.bender.learnjava.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by bender on 23.10.16.
 */
public class MyRMIServer extends UnicastRemoteObject implements RemoteInterface {


    public MyRMIServer() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, world!";
    }
}
