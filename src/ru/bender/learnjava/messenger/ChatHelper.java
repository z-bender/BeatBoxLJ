package ru.bender.learnjava.messenger;

import java.io.*;
import java.net.Socket;

/**
 * Created by bender on 16.10.16.
 */
public class ChatHelper {

    static PrintWriter getPrintWriterForSocket(Socket socket) throws IOException {
        if (socket == null) {
            return null;
        }
        return new PrintWriter(socket.getOutputStream());
    }

    static BufferedReader getBufferedReaderForSocket(Socket socket) throws IOException {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            return new BufferedReader(inputStreamReader);
    }


    static Result sendMessageToSocket(Socket socket, String text) {
        if (socket == null) {
            return new Result(true, "Отстутствует соединение с сокетом");
        }

        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter printWriter = new PrintWriter(outputStreamWriter);
            printWriter.print(text);
            return new Result(false);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, "Ошибка отправки сообщения");
        }
    }
    
    static Result giveMessageFromSocket(Socket socket) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            StringBuffer fullString = new StringBuffer();
            while ((readLine = bufferedReader.readLine()) != null) {
                fullString.append(readLine);
            }
            bufferedReader.close();
            return new Result(false, fullString.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, "Ошибка чтения из сокета");
        }
    }
}
