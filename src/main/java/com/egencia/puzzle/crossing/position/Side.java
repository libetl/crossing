package com.egencia.puzzle.crossing.position;

public enum Side {
    N, NW, W, SW, S, SE, E, NE;

    static float INITIAL_DISTANCE = 300;

    public Position initialPosition () {
        final float x = this.name().contains("E") ? INITIAL_DISTANCE :
                        this.name().contains("W") ? -INITIAL_DISTANCE : 0;
        final float y = this.name().contains("N") ? INITIAL_DISTANCE :
                this.name().contains("S") ? -INITIAL_DISTANCE : 0;
        return new Position(x, y);
    }

    public Position asVector() {
        final Position position = this.initialPosition();
        return new Position(
                position.getX() == 0 ? 0 : position.getX() / Math.abs(position.getX()),
                position.getY() == 0 ? 0 : position.getY() / Math.abs(position.getY()));
    }

    public Side opposite (){
        return Side.valueOf(
                        (this.name().contains("N") ? "S" : "") +
                        (this.name().contains("S") ? "N" : "") +
                        (this.name().contains("E") ? "W" : "") +
                        (this.name().contains("W") ? "E" : ""));
    }

    public boolean wasReached(Position position) {
        return position.isFurtherThan(this.initialPosition());
    }
}