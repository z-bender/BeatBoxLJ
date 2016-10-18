package ru.bender.learnjava.beatbox3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by bender on 18.10.16.
 */
public class Helper {

    public static ObjectInputStream getObjectInputStreamBySocket(Socket socket) throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }

    public static ObjectOutputStream getObjectOutputStreamBySocket(Socket socket) throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }
}
