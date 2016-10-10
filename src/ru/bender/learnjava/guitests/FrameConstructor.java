package ru.bender.learnjava.guitests;

import javax.swing.*;

/**
 * Created by bender on 10.10.2016.
 */
public class FrameConstructor {
    public static JFrame getNewFram() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
        return frame;
    }
}
