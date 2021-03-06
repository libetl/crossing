package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.Traffic;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class Connector {

    private static final AtomicReference<Traffic> updatedTraffic = new AtomicReference<>(new Traffic(emptyList()));
    private static final AtomicReference<TrafficLightsUpdate> updatedTrafficLights = new AtomicReference<>(new TrafficLightsUpdate(emptyList()));
    private static final Driver driver = new Driver(updatedTraffic, updatedTrafficLights);

    public static void connector(boolean interactive) {
        CarSender carSender = new CarSender();
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(5000);
        policy.setMultiplier(3);
        retryTemplate.setBackOffPolicy(policy);
        retryTemplate.execute((RetryCallback<Void, IllegalStateException>) (context) -> {
            try {
                ClientBoilerplate.connectTo("ws://localhost:8080/websock-js",
                        interactive ?
                                carSender::setSession : session -> CarInjector.inject(session, driver),
                        new HashMap<String, Consumer<?>>() {{
                            this.put("/topics/currentTraffic", traffic -> receiveTraffic((Traffic) traffic));
                            this.put("/topics/trafficLights", trafficLights -> receiveTrafficLights((TrafficLightsUpdate) trafficLights));
                            if (interactive) {
                                this.put("/topics/sendCar", car -> carSender.inject(driver, (Car) car));
                            }
                        }},
                        new HashMap<String, Class<?>>() {{
                            this.put("/topics/currentTraffic", Traffic.class);
                            this.put("/topics/trafficLights", TrafficLightsUpdate.class);
                            if (interactive)
                                this.put("/topics/sendCar", Car.class);
                        }});
                return null;
            } catch (ExecutionException | RestClientException | InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        new Scanner(System.in).nextLine();
    }

    static void receiveTraffic(Traffic traffic) {
        updatedTraffic.set(traffic);
    }

    static void receiveTrafficLights(TrafficLightsUpdate trafficLightsUpdate) {
        updatedTrafficLights.set(trafficLightsUpdate);
    }
}
