package com.egencia.puzzle.crossing.traffic;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TrafficWatcher {

    @MessageMapping("/getCurrentTraffic")
    @SendTo("/topics/currentTraffic")
    public Traffic getCurrentTraffic() {
        return CurrentTraffic.getTraffic();
    }

    @MessageMapping("/addCar")
    @SendTo("/topics/currentTraffic")
    public Traffic addCar(Car car) {
        CurrentTraffic.newCar(car);
        return CurrentTraffic.getTraffic();
    }

    @MessageMapping("/moveCar")
    @SendTo("/topics/currentTraffic")
    public Traffic moveCar(MoveCarData moveCarData) {
        CurrentTraffic.moveCar(moveCarData);
        return CurrentTraffic.getTraffic();
    }

    @MessageMapping("/removeCar")
    @SendTo("/topics/currentTraffic")
    public Traffic removeCar(RemoveCarData removeCarData) {
        CurrentTraffic.removeCar(removeCarData);
        return CurrentTraffic.getTraffic();
    }

}
