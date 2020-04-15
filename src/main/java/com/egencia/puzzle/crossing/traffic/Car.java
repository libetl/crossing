package com.egencia.puzzle.crossing.traffic;

import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

import static com.egencia.puzzle.crossing.traffic.Camera.nextObstacleForward;

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
                new Situation(cameFrom.initialPosition(), behavior.getMaxSpeed() - 10, 0));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carId, car.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }

    public Car movingForward(Side direction, Traffic otherPeople, TrafficLightsUpdate trafficLights) {
        Situation until = nextObstacleForward(this, otherPeople, trafficLights);
        return this.with(this.situation.movingForward(direction, this.getBehavior(), until));
    }

}
