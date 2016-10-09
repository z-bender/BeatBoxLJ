package ru.bender.learnjava.beatbox;

import javax.sound.midi.*;

/**
 * Created by bender on 09.10.2016.
 */
public class MiniMusicPlayer1{
    public static void main(String[] args) {
        MiniMusicPlayer1 player1 = new MiniMusicPlayer1();
        player1.go();
    }

    private void go() {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventIWant = {127};
            sequencer.addControllerEventListener(new SequencerControllerEventListener(), eventIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            for (int i = 5; i < 61; i+= 4) {
                track.add(makeEvent(144, 1, i, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, i, 100, i + 2));
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


    class SequencerControllerEventListener implements ControllerEventListener{
        @Override
        public void controlChange(ShortMessage event) {
            System.out.println("la");
        }
    }
}
