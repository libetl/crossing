package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.TrafficLightNewStatus;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.sendMessage;
import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.waitMs;
import static com.egencia.puzzle.crossing.position.Side.*;
import static com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.Status.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;

public class TraficLightsSystem {

    public static void main(String[] args) {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(5000);
        policy.setMultiplier(3);
        retryTemplate.setBackOffPolicy(policy);
        retryTemplate.execute((RetryCallback<Void, IllegalStateException>) (context) -> {
            try {
                ClientBoilerplate.connectTo("ws://localhost:8080/websock-js",
                        TraficLightsSystem::manageTraficLights,
                        emptyMap(),
                        emptyMap());
                return null;
            } catch (ExecutionException | InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        new Scanner(System.in).nextLine();
    }

    private static void manageTraficLights(StompSession session) {
        while (true) {
            sendMessage(session, "/app/setTrafficLights",
                    new TrafficLightsUpdate(
                            asList(new TrafficLightNewStatus(N, ORANGE),
                                    new TrafficLightNewStatus(S, ORANGE),
                                    new TrafficLightNewStatus(E, RED),
                                    new TrafficLightNewStatus(W, RED))));
            waitMs(2000);
            sendMessage(session, "/app/setTrafficLights",
                    new TrafficLightsUpdate(
                            asList(new TrafficLightNewStatus(N, RED),
                                    new TrafficLightNewStatus(S, RED),
                                    new TrafficLightNewStatus(E, RED),
                                    new TrafficLightNewStatus(W, RED))));
            waitMs(1000);
            sendMessage(session, "/app/setTrafficLights",
                    new TrafficLightsUpdate(
                            asList(new TrafficLightNewStatus(N, RED),
                                    new TrafficLightNewStatus(S, RED),
                                    new TrafficLightNewStatus(E, GREEN),
                                    new TrafficLightNewStatus(W, GREEN))));
            waitMs(35000);
            sendMessage(session, "/app/setTrafficLights",
                    new TrafficLightsUpdate(
                            asList(new TrafficLightNewStatus(N, RED),
                                    new TrafficLightNewStatus(S, RED),
                                    new TrafficLightNewStatus(E, ORANGE),
                                    new TrafficLightNewStatus(W, ORANGE))));
            waitMs(2000);
            sendMessage(session, "/app/setTrafficLights",
                    new TrafficLightsUpdate(
                            asList(new TrafficLightNewStatus(N, RED),
                                    new TrafficLightNewStatus(S, RED),
                                    new TrafficLightNewStatus(E, RED),
                                    new TrafficLightNewStatus(W, RED))));
            waitMs(1000);
            sendMessage(session, "/app/setTrafficLights",
                    new TrafficLightsUpdate(
                            asList(new TrafficLightNewStatus(N, GREEN),
                                    new TrafficLightNewStatus(S, GREEN),
                                    new TrafficLightNewStatus(E, RED),
                                    new TrafficLightNewStatus(W, RED))));
            waitMs(35000);
        }
    }


}
