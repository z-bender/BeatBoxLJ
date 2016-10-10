package ru.bender.learnjava.beatbox;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by bender on 09.10.2016.
 */
public class MiniMusicPlayer1{
    public static void main(String[] args) {
        MiniMusicPlayer1 player1 = new MiniMusicPlayer1();
        player1.go();
    }

    JFrame frame;
    MusicVisualizatorPanel vizualizator;

    private void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        vizualizator = new MusicVisualizatorPanel();
        frame.getContentPane().add(BorderLayout.CENTER, vizualizator);


        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventIWant = {127};
            sequencer.addControllerEventListener(vizualizator, eventIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            for (int i = 5; i < 61; i+= 4) {
                int randomNote = (int) (Math.random() * 100) + 1;
                track.add(makeEvent(144, 1, randomNote, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, randomNote, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent midiEvent = null;
        try {
            ShortMessage m = new ShortMessage();
            m.setMessage(comd, chan, one, two);
            midiEvent = new MidiEvent(m, tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return midiEvent;
    }

}
