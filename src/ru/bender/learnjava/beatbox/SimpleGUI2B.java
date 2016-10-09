package ru.bender.learnjava.beatbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bender on 09.10.2016.
 */
public class SimpleGUI2B {
    public static void main(String[] args) {
        SimpleGUI2B gui = new SimpleGUI2B();
        gui.makeGUI();
    }

    JFrame mainFrame;
    JButton button1;
    MyIncludedDrawPanel drawPanel;
    int x, y;
    Timer timer;

    private void makeGUI() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 400);
        mainFrame.setVisible(true);

        button1 = new JButton("Click me");
        mainFrame.getContentPane().add(BorderLayout.SOUTH, button1);
        button1.addActionListener(new Button1Listener());
        drawPanel = new MyIncludedDrawPanel();
        mainFrame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        timer = new Timer(50, new TimerListener());
    }

    private void moveShape() {
        x = 0;
        y = 0;
        timer.start();
    }

    class Button1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            moveShape();
        }
    }

    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawPanel.repaint();
            x++;
            y++;
            if (x > drawPanel.getHeight()) {
                timer.stop();
            }
        }
    }



    class MyIncludedDrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(MyDrawPanel.getRandColor());
            g2d.fill3DRect(x, y, 30, 30, true);
        }

    }

}
