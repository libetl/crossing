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


}
