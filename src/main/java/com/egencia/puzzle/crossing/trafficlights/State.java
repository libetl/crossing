package com.egencia.puzzle.crossing.trafficlights;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import com.egencia.puzzle.crossing.position.Side;

import static com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.Status.GREEN;
import static java.util.stream.Collectors.toList;

class State {
    static AtomicInteger TRAFFIC_LIGHTS_TOTAL = new AtomicInteger(4);
    static AtomicReference<TrafficLightsUpdate> LAST_UPDATE = new AtomicReference<>(
            new TrafficLightsUpdate(IntStream.range(0, TRAFFIC_LIGHTS_TOTAL.get())
                    .boxed().map(i ->
                            new TrafficLightsUpdate.TrafficLightNewStatus(i,
                                    Side.values()[(int)(i * 1.0 / TRAFFIC_LIGHTS_TOTAL.get() * Side.values().length)], GREEN))
                    .collect(toList())));
}
