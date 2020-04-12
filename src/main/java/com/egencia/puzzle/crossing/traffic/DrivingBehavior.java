package com.egencia.puzzle.crossing.traffic;

import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;

public enum DrivingBehavior {
    CALM(45, 11, 0.9f),
    AGGRESSIVE(50, 9, 1.9f),
    SUICIDAL(70, 8, 2.9f);

    private final int maxSpeed;
    private final float distanceWithNextObstacle;
    private final float accelerationAmplitude;

    DrivingBehavior(int maxSpeed, float distanceWithNextObstacle, float accelerationAmplitude) {
        this.maxSpeed = maxSpeed;
        this.distanceWithNextObstacle = distanceWithNextObstacle;
        this.accelerationAmplitude = accelerationAmplitude;
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

    public float getAccelerationAmplitude() {
        return accelerationAmplitude;
    }
}
