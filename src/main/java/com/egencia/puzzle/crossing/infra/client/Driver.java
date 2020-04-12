package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.MoveCarData;
import com.egencia.puzzle.crossing.traffic.RemoveCarData;
import com.egencia.puzzle.crossing.traffic.Traffic;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.sendMessage;
import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.waitMs;

public class Driver {

    private final AtomicReference<Traffic> updatedTraffic;
    private final AtomicReference<TrafficLightsUpdate> updatedTrafficLights;

    public Driver(){
        this(new AtomicReference<>(new Traffic(new ArrayList<>())),
                new AtomicReference<>(new TrafficLightsUpdate(new ArrayList<>())));
    }
    public Driver(Traffic traffic, TrafficLightsUpdate trafficLightsUpdate){
        this(new AtomicReference<>(traffic), new AtomicReference<>(trafficLightsUpdate));
    }
    public Driver(AtomicReference<Traffic> updatedTraffic, AtomicReference<TrafficLightsUpdate> updatedTrafficLights) {
        this.updatedTraffic = updatedTraffic;
        this.updatedTrafficLights = updatedTrafficLights;
    }

    public void drive(StompSession session, Car car){
        Car updatedCar = car;
        Side direction = car.getCameFrom().opposite();

        while (!direction.wasReached(updatedCar.getSituation().getPosition(), direction)){
            waitMs(100);
            updatedCar = updatedCar.movingForward(direction, updatedTraffic.get(), updatedTrafficLights.get());
            sendMessage(session, "/app/moveCar",
                    new MoveCarData(updatedCar.getCarId(), updatedCar.getSituation()));
        }

        sendMessage(session, "/app/removeCar",  new RemoveCarData(updatedCar.getCarId()));
    }
}
