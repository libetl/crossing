package com.egencia.puzzle.crossing;

import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;

public class TrafficLightsClientHacker {
    public static void hackState(TrafficLightsUpdate update) {
        CarInjector.receiveTrafficLights(update);
    }
}
