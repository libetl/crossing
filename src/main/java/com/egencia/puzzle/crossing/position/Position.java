package com.egencia.puzzle.crossing.position;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    public float distanceFrom(Position position){
        return (float) Math.sqrt(Math.pow(this.x - position.x, 2) + Math.pow(this.y - position.y, 2));
    }

    public boolean isFurtherThan(Position position, Side direction) {
        Position finalPosition = direction.initialPosition();
        Position initialPosition = direction.opposite().initialPosition();
        if ((Side.fromPosition(this) != Side.fromPosition(finalPosition) &&
                Side.fromPosition(this) != Side.fromPosition(initialPosition)) ||
                (Side.fromPosition(position) != Side.fromPosition(finalPosition) &&
                        Side.fromPosition(position) != Side.fromPosition(initialPosition)))
            return false;

        final float invertedThisX = finalPosition.x > initialPosition.x ? this.x : finalPosition.x - this.x;
        final float invertedThisY = finalPosition.y > initialPosition.y ? this.y : finalPosition.y - this.y;
        final float invertedPositionX = finalPosition.x > initialPosition.x ? position.x : finalPosition.x - position.x;
        final float invertedPositionY = finalPosition.y > initialPosition.y ? position.y : finalPosition.y - position.y;

        final float absoluteThisX = invertedThisX + Math.max(finalPosition.x, initialPosition.x);
        final float absoluteThisY = invertedThisY + Math.max(finalPosition.y, initialPosition.y);
        final float absolutePositionX = invertedPositionX + Math.max(finalPosition.x, initialPosition.x);
        final float absolutePositionY = invertedPositionY + Math.max(finalPosition.y, initialPosition.y);

        return (finalPosition.getX() == initialPosition.getX() || absoluteThisX > absolutePositionX) &&
                (finalPosition.getY() == initialPosition.getY() || absoluteThisY > absolutePositionY);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Float.compare(position.x, x) == 0 &&
                Float.compare(position.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
