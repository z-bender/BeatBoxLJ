package ru.bender.learnjava.testsockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bender on 16.10.16.
 */
public class TestServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            serverSocket = new ServerSocket(5000);
            Socket socket = serverSocket.accept();

            while (true) {
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                System.out.println(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
