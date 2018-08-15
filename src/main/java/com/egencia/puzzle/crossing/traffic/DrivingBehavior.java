package com.egencia.puzzle.crossing.traffic;

import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;

public enum DrivingBehavior {
    CALM(45, 3), AGGRESSIVE(50, 1), SUICIDAL(70, 0.2f);

    private final int maxSpeed;
    private final float distanceWithNextObstacle;

    DrivingBehavior(int maxSpeed, float distanceWithNextObstacle) {
        this.maxSpeed = maxSpeed;
        this.distanceWithNextObstacle = distanceWithNextObstacle;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public float getDistanceWithNextObstacle() {
        return distanceWithNextObstacle;
    }

    public boolean brakingFor(TrafficLightsUpdate.Status newStatus) {
        switch (newStatus){
            case RED:return true;
            case ORANGE:return this == CALM;
            case GREEN:return false;
        }
        return false;
    }
}
