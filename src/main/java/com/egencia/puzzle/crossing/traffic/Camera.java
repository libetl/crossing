package com.egencia.puzzle.crossing.traffic;

import com.egencia.puzzle.crossing.position.Position;
import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;

import java.util.function.Predicate;

public class Camera {

    public static Situation nextObstacleForward(Car car, Traffic otherPeople, TrafficLightsUpdate trafficLights) {
        Side from = car.getCameFrom();
        Side direction = from.opposite();
        Position pointOfView = car.getSituation().getPosition();
        Position finalPosition = direction.initialPosition();
        Situation finalSituation = new Situation(finalPosition, 50, 0);
        final Situation nearestCarSituation = otherPeople.getCars().stream()
                .filter(theCar -> !theCar.equals(car))
                .filter(theCar -> theCar.getCameFrom() == car.getCameFrom())
                .map(Car::getSituation)
                .filter(carSituation -> carSituation.getPosition().isFurtherThan(pointOfView, direction))
                .sorted((s1, s2) -> (s2.getPosition().isFurtherThan(s1.getPosition(), direction) ? 1 :
                        s1.getPosition().isFurtherThan(s2.getPosition(), direction) ? -1 : 0))
                .findFirst().orElse(finalSituation);
        final Position trafficLightPosition = trafficLights.getNewStatuses().stream()
                .filter(status -> car.getBehavior().brakingFor(status.getNewStatus()))
                .filter(status -> status.getSide() == from).findFirst()
                .map(TrafficLightsUpdate.TrafficLightNewStatus::getSide).map(side -> side.asVector(10))
                .filter(itsPosition -> itsPosition.isFurtherThan(pointOfView, direction))
                .orElse(finalPosition);
        return nearestCarSituation.getPosition().isFurtherThan(trafficLightPosition, direction) ?
                new Situation(trafficLightPosition, 0, 0) :
                nearestCarSituation;
    }

}
