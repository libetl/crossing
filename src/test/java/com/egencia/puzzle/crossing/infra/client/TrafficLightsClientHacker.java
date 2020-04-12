package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;

public class TrafficLightsClientHacker {
    public static void hackState(TrafficLightsUpdate update) {
        Connector.receiveTrafficLights(update);
    }
}
