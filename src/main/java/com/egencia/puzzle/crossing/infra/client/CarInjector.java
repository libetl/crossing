package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.traffic.Car;
import org.springframework.messaging.simp.stomp.StompSession;

import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.*;

public class CarInjector {

    public static void inject(StompSession session, Driver driver) {
        while(true) {
            waitRandomTime();
            Car car = randomCar();
            sendMessage(session, "/app/addCar", car);
            new Thread(() -> driver.drive(session, car)).start();
        }
    }
}
