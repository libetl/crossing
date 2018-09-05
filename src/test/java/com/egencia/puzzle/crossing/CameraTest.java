package com.egencia.puzzle.crossing;

import com.egencia.puzzle.crossing.position.Position;
import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.traffic.Camera;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.DrivingBehavior;
import com.egencia.puzzle.crossing.traffic.Situation;
import com.egencia.puzzle.crossing.traffic.Traffic;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import org.junit.Test;

import java.util.Collections;
import java.util.UUID;

import static java.util.Arrays.asList;

public class CameraTest {

    @Test
    public void test_north_direction(){
        Car north1 = new Car(UUID.randomUUID(), Side.N, DrivingBehavior.CALM)
                .with(new Situation(new Position(0, -750), 1, 1));
        Car north2 = new Car(UUID.randomUUID(), Side.N, DrivingBehavior.CALM)
                .with(new Situation(new Position(0, -450), 1, 1));
        Car north3 = new Car(UUID.randomUUID(), Side.N, DrivingBehavior.CALM)
                .with(new Situation(new Position(0, -250), 1, 1));

        Traffic updatedTraffic = new Traffic(asList(north1, north2, north3));
        TrafficLightsUpdate trafficLights = new TrafficLightsUpdate(Collections.emptyList());

        Situation nearestCarSituation = Camera.nextObstacleForward(north1, updatedTraffic, trafficLights);
    }
}
