package ru.bender.learnjava.guitests;

import java.io.Serializable;

/**
 * Created by bender on 16.10.16.
 */
class MySerializableObject implements Serializable {
    transient String x;
    String y;

    public MySerializableObject(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }
}
