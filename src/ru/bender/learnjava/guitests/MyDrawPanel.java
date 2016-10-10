package ru.bender.learnjava.guitests;

import com.sun.prism.paint.Gradient;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by bender on 09.10.2016.
 */
public class MyDrawPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        /*g.setColor(Color.orange);
        g.fillRect(20, 50, 100, 100);*/

        /*Image myImage = new ImageIcon("D:\\Dropbox\\фоты\\NMDdA3RoF6M.jpg").getImage();
        g.drawImage(myImage, 10, 10, this);*/

        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(getRandColor());
        int randY = (int) (Math.random() * this.getWidth());
        int randX = (int) (Math.random() * this.getHeight());
        int randDiametr = (int) (Math.random() * 150);
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.fill3DRect(randX - 100, randY - 100, randDiametr, randDiametr, true);
            g2d.fillRect(randX, randY, randDiametr, randDiametr);
        } else {
            g.fillOval(randX, randY, randDiametr, randDiametr);
        }

        GradientPaint gradient = new GradientPaint(70, 70, getRandColor(), 150, 150, getRandColor());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(gradient);
        g2d.fillOval(70, 70, 100, 100);

    }

    static Color getRandColor() {
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        return new Color(red, green, blue);
    }
}
