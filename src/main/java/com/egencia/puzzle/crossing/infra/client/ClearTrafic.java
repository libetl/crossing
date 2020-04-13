package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.TrafficLightNewStatus;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.sendMessage;
import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.waitMs;
import static com.egencia.puzzle.crossing.position.Side.*;
import static com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.Status.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;

public class ClearTrafic {

    public static void main(String[] args) {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(5000);
        policy.setMultiplier(3);
        retryTemplate.setBackOffPolicy(policy);
        retryTemplate.execute((RetryCallback<Void, IllegalStateException>) (context) -> {
            try {
                ClientBoilerplate.connectTo("ws://localhost:8080/websock-js",
                        session ->
                                sendMessage(session, "/app/clearTrafic", null),
                        emptyMap(),
                        emptyMap());
                return null;
            } catch (ExecutionException | InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        waitMs(3000);
    }
}
