package ru.bender.learnjava.beatbox3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by bender on 18.10.16.
 */
public class BeatBoxServer {
    public static final int SERVER_PORT = 3900;
    private ServerSocket serverSocket;
    private ArrayList<ObjectOutputStream> outputStreams;


    public static void main(String[] args) {
        try {
            BeatBoxServer server = new BeatBoxServer();
            server.waitConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BeatBoxServer() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        outputStreams = new ArrayList<>();
        System.out.println("Server was started");
    }

    private void waitConnections() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.printf("A %s%n connected", newSocket.getPort());
                Thread newConnectionThread = new Thread(new ClientConnection(newSocket));
                newConnectionThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Client Connection Error");
            }
        }
    }


    private void sendMessageToClients(BeatBoxMessage outgoingMessage) {
        for (ObjectOutputStream stream: outputStreams) {
            try {
                stream.writeObject(outgoingMessage);
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientConnection implements Runnable {

        Socket socket;

        public ClientConnection(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                outputStreams.add(Helper.getObjectOutputStreamBySocket(socket));
                ObjectInputStream inputStream = Helper.getObjectInputStreamBySocket(socket);
                BeatBoxMessage message;
                while ((message = (BeatBoxMessage) inputStream.readObject()) != null) {
                    System.out.println("(message) " + message);
                    sendMessageToClients(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in send/recive client message");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Object deserialize error");
            }
        }

    }
}
