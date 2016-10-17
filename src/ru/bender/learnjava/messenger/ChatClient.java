package ru.bender.learnjava.messenger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by bender on 16.10.16.
 */
public class ChatClient {

    private static final String SERVER_IP = "127.0.0.1";

    JFrame frame;
    JTextField messageField;
    JButton sendButton;
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.go();
    }

    public ChatClient() {
        makeGui();
    }

    private void go() {
        connectToServer();
    }

    private void makeGui() {
        frame = new JFrame("Чат");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        messageField = new JTextField(40);
        panel.add(messageField);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        panel.add(sendButton);

        frame.getContentPane().add(panel);
        frame.setSize(400, 120);
        frame.setVisible(true);
    }

    private void showMessageDialog(String text) {
        JOptionPane.showMessageDialog(frame, text);
    }

    private boolean connectToServer() {
        try {
            socket = new Socket(SERVER_IP, ChatServer.SERVER_PORT);
            bufferedReader = ChatHelper.getBufferedReaderForSocket(socket);
            printWriter = ChatHelper.getPrintWriterForSocket(socket);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog("Ошибка подключения к серверу");
            return false;
        }
    }



    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(printWriter == null) {
                showMessageDialog("Отсутствует подключение к серверу");
                return;
            }
            printWriter.println(messageField.getText());
            printWriter.flush();

            messageField.setText("");
            messageField.requestFocus();
        }
    }

}
