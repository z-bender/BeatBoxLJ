package ru.bender.learnjava.beatbox2;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;

import static ru.bender.learnjava.beatbox.MiniMusicPlayer1.makeEvent;

/**
 * Created by bender on 11.10.2016.
 */
public class BeatBox {
    JFrame theFrame;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat",
            "Open Hi-Hat","Acoustic Snare", "Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
            "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo",
            "Open Hi Conga"};
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel backround = new JPanel(layout);
        backround.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        checkBoxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Up tempo");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Down tempo");
        stop.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton save = new JButton("Save");
        save.addActionListener(new MySaveListener());
        buttonBox.add(save);

        JButton load = new JButton("Load");
        load.addActionListener(new MyLoadListener());
        buttonBox.add(load);


        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < instrumentNames.length; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        backround.add(BorderLayout.EAST, buttonBox);
        backround.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(BorderLayout.CENTER, backround);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        backround.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();
        
        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    public void buildTrackAndStart() {
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = (JCheckBox) checkBoxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 9, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        serializeObject(checkBoxList, "sequencer2.save");
    }

    private void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    private void serializeObject(ArrayList<JCheckBox> checkBoxes, String fileName) {
        try {
            FileOutputStream fileStream = new FileOutputStream(fileName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(checkBoxes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<JCheckBox> deserializeCheckBoxList(String fileName) {
        try {
            FileInputStream fileStream = new FileInputStream(fileName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            return (ArrayList<JCheckBox>)objectStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    class MyDownTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * .97));
        }
    }

    class MySaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            serializeObject(checkBoxList, "sequencer.save");
        }
    }

    class MyLoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkBoxList = deserializeCheckBoxList("sequencer2.save");
            theFrame.repaint();
        }
    }

}
