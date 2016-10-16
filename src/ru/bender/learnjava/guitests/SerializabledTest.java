package ru.bender.learnjava.guitests;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by bender on 16.10.16.
 */
public class SerializabledTest {
    private static final String SER_FILE_NAME = "mySerObj.txt";
    private static final String FILE_NAME2 = "saveInputs.txt";
    private static final String SPLITER = "/";

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

    private void saveInputsToFile(String fileName) {
        File file = new File(fileName);
        saveInputsToFile(file);
    }

    private void saveInputsToFile(File file) {
        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            FileWriter bw = new FileWriter(file, true);
            bw.append(textField1.getText() + SPLITER + textField2.getText() + System.lineSeparator());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadInputsFromFile(String fileName) {
        File file = new File(fileName);
        return loadInputsFromFile(file);
    }

    private String loadInputsFromFile(File file) {
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            line = br.readLine();
        } catch (IOException e) {
        }
        return line;
    }

    class SaveBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
//            MySerializableObject serObj = new MySerializableObject(textField1.getText(), textField2.getText());
//            saveObject(serObj, SER_FILE_NAME);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(frame);
            saveInputsToFile(fileChooser.getSelectedFile());

        }
    }

    class LoadBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
//            MySerializableObject serObj = getSerObj(SER_FILE_NAME);
//            textField1.setText(serObj.getX());
//            textField2.setText(serObj.getY());
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(frame);

            String loadedLine = loadInputsFromFile(fileChooser.getSelectedFile());
            if (!loadedLine.isEmpty()) {
                String[] inputs = loadedLine.split(SPLITER);
                textField1.setText(inputs[0]);
                textField2.setText(inputs[1]);
            }
        }
    }


}
