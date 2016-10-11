package ru.bender.learnjava.guitests;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Created by bender on 10.10.2016.
 */
public class Panel1 {
    public static void main(String[] args) {
        Panel1 gui = new Panel1();
        gui.go();
    }

    JScrollPane scrollPane;
    JFrame frame;
    JPanel panel;

    private void go() {
        frame = FrameConstructor.getNewFram();
        panel = new JPanel();
        panel.setBackground(Color.darkGray);
//        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setContentPane(panel);
        /*panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton button = new JButton("click me");
        panel.add(button);
        JButton buttonTwo = new JButton("click me more");
        panel.add(buttonTwo);
        JButton buttonThree = new JButton("click me more and more and more and more");
        panel.add(buttonThree);
        JTextField textField = new JTextField("Text", 30);
        JTextField textField2 = new JTextField("Enter password:");

        panel.add(textField);
        panel.add(textField2);


        JTextArea text = new JTextArea(10, 20);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scroll);
        frame.repaint();*/

//        frame.getContentPane().add(BorderLayout.WEST, textField);


        JList list = new JList(new String[]{"item1", "item2", "item3"});
        scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);

    }

    class listSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
//            System.out.println(list.ge);
        }
    }
}
