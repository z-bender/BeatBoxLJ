package ru.bender.learnjava.beatbox3;

import java.io.Serializable;

/**
 * Created by bender on 18.10.16.
 */
public class BeatBoxMessage implements Serializable {
    String text;
    boolean[] checkBoxesStates;
    String sender;

    @Override
    public String toString() {
        return (sender + ": " + text);
    }
}
