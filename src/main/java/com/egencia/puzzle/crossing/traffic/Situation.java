package com.egencia.puzzle.crossing.traffic;

import com.egencia.puzzle.crossing.position.Position;
import com.egencia.puzzle.crossing.position.Side;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.egencia.puzzle.crossing.traffic.DrivingBehavior.AGGRESSIVE;
import static com.egencia.puzzle.crossing.traffic.DrivingBehavior.SUICIDAL;

public class Situation {

    private final Position position;
    private final float speed;
    private final float acceleration;

    @JsonCreator
    public Situation(@JsonProperty("position") Position position,
                     @JsonProperty("speed") float speed,
                     @JsonProperty("acceleration") float acceleration) {
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
    }

    public Position getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAcceleration() {
        return acceleration;
    }


    public Situation movingForward(Side direction, DrivingBehavior behavior) {
        Position vector = direction.asVector();
        boolean diagonal = Math.abs(vector.getX()) + Math.abs(vector.getY()) == 2;
        final float coeff = behavior == SUICIDAL ? 2f : behavior == AGGRESSIVE ? 1.4f : 1.0f;
        final float deltaX = (float) (diagonal ? Math.sqrt(coeff) : coeff) * vector.getX();
        final float deltaY = (float) (diagonal ? Math.sqrt(coeff) : coeff) * vector.getY();
        return new Situation(
                new Position(this.getPosition().getX() + deltaX, this.getPosition().getY() + deltaY),
                0f, 0f);
    }

}
