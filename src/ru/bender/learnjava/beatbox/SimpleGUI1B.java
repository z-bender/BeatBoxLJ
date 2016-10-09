package ru.bender.learnjava.beatbox;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bender on 09.10.2016.
 */
public class SimpleGUI1B {

    JButton button;
    JButton button2;
    JLabel label1;
    JFrame mainFrame;
    MyDrawPanel myDrawPanel;

    public static void main(String[] args) {
        SimpleGUI1B gui = new SimpleGUI1B();
        gui.go();
    }

    private void go() {
        mainFrame = new JFrame();
        button = new JButton("click me");
        button2 = new JButton("change label");
        label1 = new JLabel("Label");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myDrawPanel = new MyDrawPanel();
        mainFrame.getContentPane().add(BorderLayout.SOUTH ,button);
        mainFrame.getContentPane().add(BorderLayout.EAST ,button2);
        mainFrame.getContentPane().add(BorderLayout.WEST, label1);
        mainFrame.getContentPane().add(BorderLayout.CENTER, myDrawPanel);
//        button.setSize(mainFrame.getWidth(), 40);
//        button.setLocation(40,40);

        mainFrame.setSize(300, 300);
        mainFrame.setVisible(true);
        button.addActionListener(new ButtonActionListener());
        button2.addActionListener(new Button2ActionListener());

    }


    class Button2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            label1.setText("" + (int)(Math.random() * 100));
        }
    }

    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            button.setText("I'll clicked");
            myDrawPanel.repaint();
        }
    }

}
