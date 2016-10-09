package ru.bender.learnjava.beatbox;

import javax.sound.midi.*;

/**
 * Created by bender on 08.10.2016.
 */
public class MusicTest1 {

    public static void main(String[] args) {
        MusicTest1 mt = new MusicTest1();
        mt.play();
    }

    public void play() {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            System.out.println("We get sequence");
        } catch (MidiUnavailableException e) {
            System.out.println("fail get sequence!");
        }
    }
}
