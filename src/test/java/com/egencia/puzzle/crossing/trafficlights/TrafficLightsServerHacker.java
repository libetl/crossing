package com.egencia.puzzle.crossing.trafficlights;

public class TrafficLightsServerHacker {
    public static void hackState(TrafficLightsUpdate update) {
        State.LAST_UPDATE.set(update);
    }
}
