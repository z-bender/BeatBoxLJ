package ru.bender.learnjava.messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bender on 16.10.16.
 */
public class ChatServer {
    static final int SERVER_PORT = 3500;

    ServerSocket serverSocket;


    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ChatServer() throws IOException {
        this.serverSocket = new ServerSocket(SERVER_PORT);
    }

    private void listen() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Подключился клиент - " + socket.getPort());
                Thread clientReader = new Thread(new ClientReader(socket));
                clientReader.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class ClientReader implements Runnable{
        Socket socketToClient;
        BufferedReader bufferedReader;

        public ClientReader(Socket socketToClient) {
            this.socketToClient = socketToClient;
            try {
                bufferedReader = ChatHelper.getBufferedReaderForSocket(socketToClient);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка чтения клиента");
            }
        }

        @Override
        public void run() {
            while (socketToClient.isConnected()) {
                try {
                    System.out.println(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Ошибка чтения клиента");
                }
            }
            System.out.println("Клиент отключился");
        }
    }
}
