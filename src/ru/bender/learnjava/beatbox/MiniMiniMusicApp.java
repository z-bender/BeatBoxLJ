package ru.bender.learnjava.beatbox;

import javax.sound.midi.*;
import java.util.Scanner;

/**
 * Created by bender on 08.10.2016.
 */
public class MiniMiniMusicApp {

    public static void main(String[] args) {
        MiniMiniMusicApp mini = new MiniMiniMusicApp();
        while (true) {
            int instrument = getInputInt("инструмент: ");
            int note = getInputInt("нота: ");
            mini.play(instrument, note);
        }
    }

    private static int getInputInt(String output) {
        Scanner reader = new Scanner(System.in);
        System.out.println(output);
        return reader.nextInt();
    }

    private static void play(int instrument, int note) {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);

            Track track = seq.createTrack();

            ShortMessage setInstrument = new ShortMessage();
            setInstrument.setMessage(192,1, instrument, 100);
            MidiEvent changeInstrument = new MidiEvent(setInstrument, 1);
            track.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(144,1,note, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128,1,note, 100);
            MidiEvent noteOff = new MidiEvent(b, 30);
            track.add(noteOff);

            player.setSequence(seq);
            player.start();
        } catch (MidiUnavailableException e) {
            System.out.println("MidiUvavailable");
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            System.out.println("InvalidMidiData");
            e.printStackTrace();
        }
    }

}
