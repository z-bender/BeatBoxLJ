package ru.bender.learnjava.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by bender on 23.10.16.
 */
public class ServerRunner {
    public static void main(String[] args) {
        try {
            RemoteInterface server = new MyRMIServer();
            Naming.rebind("My first server", server);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
