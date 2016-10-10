package ru.bender.learnjava.guitests;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bender on 10.10.2016.
 */
public class Panel1 {
    public static void main(String[] args) {
        Panel1 gui = new Panel1();
        gui.go();
    }

    private void go() {
        JFrame frame = FrameConstructor.getNewFram();
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
//        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton button = new JButton("click me");
        panel.add(button);
        JButton buttonTwo = new JButton("click me more");
        panel.add(buttonTwo);
        JButton buttonThree = new JButton("click me more and more and more and more");
        panel.add(buttonThree);
    }
}
