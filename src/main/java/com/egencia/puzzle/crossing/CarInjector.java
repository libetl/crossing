package com.egencia.puzzle.crossing;

import com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate;
import com.egencia.puzzle.crossing.position.Side;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.MoveCarData;
import com.egencia.puzzle.crossing.traffic.RemoveCarData;
import com.egencia.puzzle.crossing.traffic.Traffic;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.randomCar;
import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.sendMessage;
import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.waitMs;
import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.waitRandomTime;
import static java.util.Collections.emptyList;

public class CarInjector {

    private static Side from;
    private static AtomicReference<Traffic> updatedTraffic = new AtomicReference<>(new Traffic(emptyList()));
    private static AtomicReference<TrafficLightsUpdate> updatedTrafficLights = new AtomicReference<>(new TrafficLightsUpdate(emptyList()));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        from = args.length == 0 ? null : Side.valueOf(args[0]);
        ClientBoilerplate.connectTo("ws://localhost:8080/websock-js",
                CarInjector::inject,
                new HashMap<String, Consumer<?>>(){{
                    this.put("/topics/currentTraffic", traffic -> receiveTraffic((Traffic)traffic));
                    this.put("/topics/trafficLights", trafficLights -> receiveTrafficLights((TrafficLightsUpdate)trafficLights)); }},
                new HashMap<String, Class<?>>(){{
                    this.put("/topics/currentTraffic", Traffic.class);
                    this.put("/topics/trafficLights", TrafficLightsUpdate.class);
                }});
        new Scanner(System.in).nextLine();
    }

    private static void receiveTraffic(Traffic traffic) {
        updatedTraffic.set(traffic);
    }

    private static void receiveTrafficLights(TrafficLightsUpdate trafficLightsUpdate) {
        updatedTrafficLights.set(trafficLightsUpdate);
    }

    private static void inject(StompSession session) {
        while(true) {
            waitRandomTime();
            Car car = randomCar(from);
            sendMessage(session, "/app/addCar", car);
            new Thread(() -> drive(session, car)).start();
        }
    }

    private static void drive(StompSession session, Car car){
        Car updatedCar = car;
        Side direction = car.getCameFrom().opposite();

        while (!direction.wasReached(updatedCar.getSituation().getPosition())){
            waitMs(100);
            updatedCar = updatedCar.movingForward(direction, updatedTraffic.get(), updatedTrafficLights.get());
            sendMessage(session, "/app/moveCar",
                    new MoveCarData(updatedCar.getCarId(), updatedCar.getSituation()));
        }

        sendMessage(session, "/app/removeCar",  new RemoveCarData(updatedCar.getCarId()));
    }
}
