package com.egencia.puzzle.crossing.position;

import com.egencia.puzzle.crossing.traffic.DrivingBehavior;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.egencia.puzzle.crossing.traffic.DrivingBehavior.AGGRESSIVE;
import static com.egencia.puzzle.crossing.traffic.DrivingBehavior.SUICIDAL;

public class Position {
    private final float x;
    private final float y;

    @JsonCreator
    public Position(@JsonProperty("x") float x, @JsonProperty("y") float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isFurtherThan(Position position) {
        if (position.x < 0 && this.x > 0) return false;
        if (position.x > 0 && this.x < 0) return false;
        if (position.y < 0 && this.y > 0) return false;
        if (position.y > 0 && this.y < 0) return false;

        return Math.abs(this.x) >= Math.abs(position.x) &&
                Math.abs(this.y) >= Math.abs(position.y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
