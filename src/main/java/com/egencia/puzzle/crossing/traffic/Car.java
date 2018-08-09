package com.egencia.puzzle.crossing.traffic;

import com.egencia.puzzle.crossing.position.Position;
import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Car {

    private final UUID carId;
    private final Side cameFrom;
    private final DrivingBehavior behavior;
    private final Situation situation;

    @JsonCreator
    public Car(@JsonProperty("carId") UUID carId,
               @JsonProperty("cameFrom") Side cameFrom,
               @JsonProperty("behavior") DrivingBehavior behavior) {
        this(carId, cameFrom, behavior,
                new Situation(cameFrom.initialPosition(), 0, 0));
    }

    private Car(UUID carId, Side cameFrom, DrivingBehavior behavior, Situation situation) {
        this.carId = carId;
        this.cameFrom = cameFrom;
        this.behavior = behavior;
        this.situation = situation;
    }

    public UUID getCarId() {
        return carId;
    }

    public Side getCameFrom() {
        return cameFrom;
    }

    public DrivingBehavior getBehavior() {
        return behavior;
    }

    public Situation getSituation() {
        return situation;
    }

    public Car with(Situation newSituation) {
        return new Car(this.carId, this.cameFrom, this.behavior, newSituation);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", cameFrom=" + cameFrom +
                ", behavior=" + behavior +
                ", situation=" + situation +
                '}';
    }

    public Car movingForward(Side direction, Traffic otherPeople, TrafficLightsUpdate trafficLights) {
        return this.with(this.situation.movingForward(direction, this.getBehavior()));
    }
}
