package com.egencia.puzzle.crossing.trafficlights;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TrafficLightsController {

    @MessageMapping("/getTrafficLights")
    @SendTo("/topics/trafficLights")
    public TrafficLightsUpdate getTrafficLights() {
        return State.LAST_UPDATE.get();
    }

    @MessageMapping("/setTrafficLights")
    @SendTo("/topics/trafficLights")
    public TrafficLightsUpdate setTrafficLights(TrafficLightsUpdate update) {
        State.LAST_UPDATE.set(update);
        return update;
    }


}
