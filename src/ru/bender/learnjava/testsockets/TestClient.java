package ru.bender.learnjava.testsockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by bender on 16.10.16.
 */
public class TestClient {
    public static void main(String[] args) {
        Socket socket;
        OutputStreamWriter outputStreamWriter = null;
        PrintWriter printWriter = null;
        try {
            socket = new Socket("127.0.0.1", 5000);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            printWriter = new PrintWriter(outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner consoleReader = new Scanner(System.in);

        while (true) {
            String textForSend = consoleReader.next();
            printWriter.println(textForSend);
            printWriter.flush();
        }
    }
}
