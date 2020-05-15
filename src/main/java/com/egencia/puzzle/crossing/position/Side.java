package com.egencia.puzzle.crossing.position;

import java.util.Random;

import static java.util.Arrays.asList;

public enum Side {
    N, NW, W, SW, S, SE, E, NE;

    static float INITIAL_DISTANCE = 900;

    public static Side randomBetween4Sides(){
        return asList(N, S, E, W).get(new Random().nextInt(4));
    }

    public static Side fromPosition (Position p){
        return valueOf((p.getY() > 0 ? "N" : "") +
                        (p.getY() < 0 ? "S" : "") +
                        (p.getX() > 0 ? "E" : "") +
                        (p.getX() < 0 ? "W" : ""));
    }

    public static boolean isEdge(float value) {
        return Math.abs(value) == INITIAL_DISTANCE;
    }

    public Position initialPosition () {
        final float x = this.name().contains("E") ? INITIAL_DISTANCE :
                        this.name().contains("W") ? -INITIAL_DISTANCE : 0;
        final float y = this.name().contains("N") ? INITIAL_DISTANCE :
                this.name().contains("S") ? -INITIAL_DISTANCE : 0;
        return new Position(x, y);
    }

    public Position asVector() {
        return this.asVector(1);
    }

    public Position asVector(int norm) {
        final Position position = this.initialPosition();
        return new Position(
                position.getX() == 0 ? 0 : position.getX() / Math.abs(position.getX()) * norm,
                position.getY() == 0 ? 0 : position.getY() / Math.abs(position.getY()) * norm);
    }

    public Side opposite (){
        return Side.valueOf(
                        (this.name().contains("N") ? "S" : "") +
                        (this.name().contains("S") ? "N" : "") +
                        (this.name().contains("E") ? "W" : "") +
                        (this.name().contains("W") ? "E" : ""));
    }

    public boolean wasReached(Position position, Side direction) {
        return position.isFurtherThan(this.initialPosition(), direction);
    }
}