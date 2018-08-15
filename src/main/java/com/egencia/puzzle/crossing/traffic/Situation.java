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
    private static final float ACCELERATION_AMPLITUDE = 0.9f;
    private final int willDecideToBrake;
    private static final int NO = -1;
    private static final int NOW = 0;
    private static final int YES = 3;
    private static final int YES_NOW = 0;

    public Situation(Position position,
                     float speed,
                     float acceleration){
        this(position, speed, acceleration, NO);
    }
                     @JsonCreator
    public Situation(@JsonProperty("position") Position position,
                     @JsonProperty("speed") float speed,
                     @JsonProperty("acceleration") float acceleration,
                     @JsonProperty("willDecideToBrake") int willDecideToBrake) {
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.willDecideToBrake = willDecideToBrake;
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

    public Situation movingForward(Side direction, DrivingBehavior behavior, Situation nextObstacle) {
        int iterationsBeforeSpeedAdaptation = (int)Math.ceil(Math.sqrt((2 * (speed-nextObstacle.speed)) / Math.pow(ACCELERATION_AMPLITUDE, 2)));
        float nextObstacleDistance = nextObstacle.getPosition().distanceFrom(this.position) + behavior.getDistanceWithNextObstacle();
        int iterationsBeforeNextObstacle = (int)Math.floor(nextObstacleDistance / speed);
        boolean shouldDecideToBrake = iterationsBeforeSpeedAdaptation > iterationsBeforeNextObstacle + 1;
        int nextWillDecideToBrake = nextObstacleDistance < 10 && speed < 10 ? YES_NOW  :
                willDecideToBrake == NO && shouldDecideToBrake ?  YES :
                shouldDecideToBrake ? Math.max(willDecideToBrake - 1, NOW) : NO;
        float nextAcceleration = willDecideToBrake == NOW ? -ACCELERATION_AMPLITUDE :
                speed < behavior.getMaxSpeed() ? ACCELERATION_AMPLITUDE : 0;
        float nextSpeed = Math.max(0, speed + nextAcceleration);
        Position vector = direction.asVector();
        boolean diagonal = Math.abs(vector.getX()) + Math.abs(vector.getY()) == 2;
        final float coeff = (nextSpeed < 1E-3 ? 0 : nextSpeed) / 10;
        final float deltaX = (float) (diagonal ? Math.sqrt(coeff) : coeff) * vector.getX();
        final float deltaY = (float) (diagonal ? Math.sqrt(coeff) : coeff) * vector.getY();
        return new Situation(
                new Position(this.getPosition().getX() + deltaX, this.getPosition().getY() + deltaY),
                nextSpeed, nextAcceleration, nextWillDecideToBrake);
    }

}
