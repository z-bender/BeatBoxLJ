package ru.bender.learnjava.messenger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by bender on 16.10.16.
 */
public class ChatClient {

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.go();
    }

    private static final String SERVER_IP = "127.0.0.1";

    JFrame frame;
    Socket socket;
    JTextField messageField;
    JButton sendButton;

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
            Result result = ChatHelper.sendMessageToSocket(socket, messageField.getText());
            if (!result.getMessage().isEmpty()) {
                showMessageDialog(result.getMessage());
            }
        }
    }

}
