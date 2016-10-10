package ru.bender.learnjava.beatbox;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;

/**
 * Created by bender on 10.10.2016.
 */
public class MusicVisualizatorPanel extends JPanel implements ControllerEventListener {
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        int randWidth = (int) (Math.random() * 150) + 50;
        int randHeignt = (int) (Math.random() * 150) + 50;
        g.setColor(MyDrawPanel.getRandColor());
        g.fillRect(getCoordinateForCentredRect(getWidth(), randWidth), getCoordinateForCentredRect(getHeight(), randHeignt), randWidth, randHeignt);

    }

    private void paintRandomRect() {
        repaint();
           }

    private int getCoordinateForCentredRect(int panelLength, int rectLength) {
        return (panelLength / 2) - (rectLength / 2);
    }

    @Override
    public void controlChange(ShortMessage event) {
        paintRandomRect();
    }
}
