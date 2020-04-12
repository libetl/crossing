package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.Traffic;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;

public class Connector {

    private static final AtomicReference<Traffic> updatedTraffic = new AtomicReference<>(new Traffic(emptyList()));
    private static final AtomicReference<TrafficLightsUpdate> updatedTrafficLights = new AtomicReference<>(new TrafficLightsUpdate(emptyList()));
    private static final Driver driver = new Driver(updatedTraffic, updatedTrafficLights);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CarSender carSender = new CarSender();
        ClientBoilerplate.connectTo("ws://localhost:8080/websock-js",
                args.length > 0 && args[0].equals("interactive") ?
                        carSender::setSession : session -> CarInjector.inject(session, driver),
                new HashMap<String, Consumer<?>>() {{
                    this.put("/topics/currentTraffic", traffic -> receiveTraffic((Traffic) traffic));
                    this.put("/topics/trafficLights", trafficLights -> receiveTrafficLights((TrafficLightsUpdate) trafficLights));
                    if (args.length > 0 && args[0].equals("interactive")) {
                        this.put("/topics/sendCar", car -> carSender.inject(driver, (Car) car));
                    }
                }},
                new HashMap<String, Class<?>>() {{
                    this.put("/topics/currentTraffic", Traffic.class);
                    this.put("/topics/trafficLights", TrafficLightsUpdate.class);
                    if (args.length > 0 && args[0].equals("interactive"))
                        this.put("/topics/sendCar", Car.class);
                }});
        new Scanner(System.in).nextLine();
    }

    static void receiveTraffic(Traffic traffic) {
        updatedTraffic.set(traffic);
    }

    static void receiveTrafficLights(TrafficLightsUpdate trafficLightsUpdate) {
        updatedTrafficLights.set(trafficLightsUpdate);
    }
}
