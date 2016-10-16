package ru.bender.learnjava.guitests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by bender on 16.10.16.
 */
public class SerializabledTest {
    private static final String FILE_NAME = "mySerObj.txt";

    public static void main(String[] args) {
        SerializabledTest app = new SerializabledTest();
        app.go();
    }

    JFrame frame;
    JTextField textField1;
    JTextField textField2;
    JButton saveBtn;
    JButton loadBtn;

    private void go() {
        makeGUI();
    }

    private void makeGUI() {
        frame = new JFrame("Сериализуй его нах...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel fieldsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, fieldsPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);

        textField1 = new JTextField("0", 20);
        textField2 = new JTextField("0", 20);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.add(textField1);
        fieldsPanel.add(textField2);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveBtnListener());
        buttonsPanel.add(saveBtn);
        loadBtn = new JButton("Load");
        loadBtn.addActionListener(new LoadBtnListener());
        buttonsPanel.add(loadBtn);

        frame.setSize(200, 130);
        frame.setVisible(true);
    }

    private void saveObject(Object object, String fileName){
        try {
            FileOutputStream fileStream = new FileOutputStream(fileName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(object);
            objectStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MySerializableObject getSerObj(String fileName){
        MySerializableObject result = null;
        try {
            FileInputStream fileStream = new FileInputStream(fileName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            result = (MySerializableObject)objectStream.readObject();
            objectStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    class SaveBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MySerializableObject serObj = new MySerializableObject(textField1.getText(), textField2.getText());
            saveObject(serObj, FILE_NAME);
        }
    }

    class LoadBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MySerializableObject serObj = getSerObj(FILE_NAME);
            textField1.setText(serObj.getX());
            textField2.setText(serObj.getY());
        }
    }


}
