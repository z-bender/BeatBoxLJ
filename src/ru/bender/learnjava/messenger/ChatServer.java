package ru.bender.learnjava.messenger;

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
                Result givedMessage = ChatHelper.giveMessageFromSocket(socket);
                if (!givedMessage.isError()) {
                    ChatHelper.sendMessageToSocket(socket, "Сообщение отправлено");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
