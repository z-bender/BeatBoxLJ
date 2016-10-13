package ru.bender.learnjava.guitests;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bender on 10.10.2016.
 */
public class Button1 {
    public static void main(String[] args) {
        Button1 button = new Button1();
        button.go();
    }

    private void go() {
        JFrame frame = new JFrame("Баттон1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 300);
        frame.setVisible(true);

        JButton button1 = new JButton("This is the big button OK");
        Font font = new Font("Serif", Font.BOLD, 29);
        button1.setFont(font);
        frame.getContentPane().add(BorderLayout.NORTH, button1);

        // test comment
    }
}
