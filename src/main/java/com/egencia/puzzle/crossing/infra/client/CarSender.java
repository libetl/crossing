package com.egencia.puzzle.crossing.infra.client;

import com.egencia.puzzle.crossing.traffic.Car;
import org.springframework.messaging.simp.stomp.StompSession;

import static com.egencia.puzzle.crossing.infra.websockets.ClientBoilerplate.sendMessage;

public class CarSender {
    private StompSession session;

    public static void main(String[] args) {
        Connector.connector(true);
    }

    public CarSender(){

    }

    public CarSender(StompSession stompSession) {
        this.session = stompSession;
    }

    public void inject(Driver driver, Car car) {
        sendMessage(session, "/app/addCar", car);
        new Thread(() -> driver.drive(session, car)).start();
    }

    public void setSession(StompSession session) {
        this.session = session;
    }
}
