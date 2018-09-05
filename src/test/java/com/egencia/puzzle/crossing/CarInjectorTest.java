package com.egencia.puzzle.crossing;

import com.egencia.puzzle.crossing.position.Position;
import com.egencia.puzzle.crossing.traffic.Car;
import com.egencia.puzzle.crossing.traffic.MoveCarData;
import com.egencia.puzzle.crossing.traffic.RemoveCarData;
import com.egencia.puzzle.crossing.traffic.Situation;
import com.egencia.puzzle.crossing.traffic.Traffic;
import com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.egencia.puzzle.crossing.position.Side.*;
import static com.egencia.puzzle.crossing.traffic.DrivingBehavior.CALM;
import static com.egencia.puzzle.crossing.traffic.DrivingBehavior.SUICIDAL;
import static com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.Status.GREEN;
import static com.egencia.puzzle.crossing.trafficlights.TrafficLightsUpdate.Status.RED;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarInjectorTest {

    private Situation updatedSituation = null;
    private boolean hasEnded = false;

    @Before
    public void reinit(){
        updatedSituation = null;
        hasEnded = false;
    }

    @Test
    public void driveTest(){
        Car car = new Car(UUID.randomUUID(), SW, CALM);
        StompSession session = mock(StompSession.class);

        CarInjector.drive(session, car);

        verify(session, times(282)).send(eq("/app/moveCar"), any(MoveCarData.class));
        verify(session).send(eq("/app/removeCar"), any(RemoveCarData.class));
    }

    @Test
    public void headingInADirectionShouldCrossTheRoad(){
        Car car = new Car(UUID.randomUUID(), E, SUICIDAL);
        StompSession session = mock(StompSession.class);

        CarInjector.drive(session, car);

        assertThat(car.getSituation().getPosition()).isEqualTo(new Position(300, 0));
    }

    @Test
    public void southEastShouldGoToNorthWest(){
        Car car = new Car(UUID.randomUUID(), SE, SUICIDAL);
        StompSession session = mock(StompSession.class);

        CarInjector.drive(session, car);

        assertThat(car.getSituation().getPosition()).isEqualTo(new Position(300, -300));
    }

    @Test
    public void carShouldNotStopAtAGreenTrafficLight(){
        Car car = new Car(UUID.randomUUID(), SE, CALM);
        StompSession session = mock(StompSession.class);
        TrafficLightsClientHacker.hackState(new TrafficLightsUpdate(
                singletonList(new TrafficLightsUpdate.TrafficLightNewStatus(1, SE, GREEN))));

        CarInjector.drive(session, car);

        assertThat(car.getSituation().getPosition()).isEqualTo(new Position(300, -300));
    }

    @Test
    public void carShouldStopAtTheTrafficLight(){
        Car car = new Car(UUID.randomUUID(), SE, SUICIDAL);
        StompSession session = mock(StompSession.class);
        when(session.send(eq("/app/moveCar"), any())).then(invocation -> {
            updatedSituation = ((MoveCarData)invocation.getArguments()[1]).getNewSituation();
            return mock(StompSession.Receiptable.class);});
        when(session.send(eq("/app/removeCar"), any())).then(invocation -> {
            hasEnded = true;
            return mock(StompSession.Receiptable.class);});
        TrafficLightsClientHacker.hackState(new TrafficLightsUpdate(
                singletonList(new TrafficLightsUpdate.TrafficLightNewStatus(1, SE, RED))));

        Thread t = new Thread(() -> CarInjector.drive(session, car));
        t.start();

        while(updatedSituation == null || updatedSituation.getSpeed() > 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new AssertionError("should not fail");
            }
        }

        t.interrupt();

        assertThat(updatedSituation.getPosition()).isEqualTo(new Position(14.36793f, -14.36793f));
    }

    @Test
    public void carShouldCallRemoveCarAtTheEnd(){
        Car car = new Car(UUID.randomUUID(), SE, SUICIDAL);
        StompSession session = mock(StompSession.class);
        when(session.send(eq("/app/moveCar"), any())).then(invocation -> {
                updatedSituation = ((MoveCarData)invocation.getArguments()[1]).getNewSituation();
                return mock(StompSession.Receiptable.class);});
        when(session.send(eq("/app/removeCar"), any())).then(invocation -> {
            hasEnded = true;
            return mock(StompSession.Receiptable.class);});

        Thread t = new Thread(() -> CarInjector.drive(session, car));
        t.start();

        while(!hasEnded){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new AssertionError("should not fail");
            }
        }

        assertThat(updatedSituation.getPosition()).isEqualTo(new Position(-300.80923f, 300.80923f));
    }

    @Test
    public void suicidalCarShouldNotOverlapTheCalmCar(){
        Car calmCar = new Car(UUID.randomUUID(), SE, CALM);
        Car suicidalCar = new Car(UUID.randomUUID(), SE, SUICIDAL);

        List<Car> savedCars = new ArrayList<>(2);
        savedCars.add(calmCar);
        savedCars.add(suicidalCar);
        CarInjector.receiveTraffic(new Traffic(savedCars));
        UUID[] firstCarIdToReachTheEnd = {null};
        StompSession session = mock(StompSession.class);
        when(session.send(eq("/app/moveCar"), any())).then(invocation -> {
            MoveCarData moveCarData = (MoveCarData)invocation.getArgument(1);
            String car = moveCarData.getCarId() == calmCar.getCarId() ?
                    "calmCar    " : "suicidalCar";
            if ("calmCar    ".equals(car)){
                savedCars.set(0, calmCar.with(moveCarData.getNewSituation()));
            }else{
                savedCars.set(1, suicidalCar.with(moveCarData.getNewSituation()));
            }
            System.out.println(car + " = " + ((MoveCarData)invocation.getArgument(1)).getNewSituation().getPosition());
            return mock(StompSession.Receiptable.class);});
        when(session.send(eq("/app/removeCar"), any())).then(invocation -> {
            if (firstCarIdToReachTheEnd[0] == null){
                firstCarIdToReachTheEnd[0] = ((RemoveCarData)invocation.getArguments()[1]).getCarId();
            }
            return mock(StompSession.Receiptable.class);});

        Thread t1 = new Thread(() -> CarInjector.drive(session, calmCar));
        Thread t2 = new Thread(() -> CarInjector.drive(session, suicidalCar));
        t1.start();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new AssertionError("should not fail");
        }

        t2.start();

        while(firstCarIdToReachTheEnd[0] == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new AssertionError("should not fail");
            }
        }

        t1.interrupt();
        t2.interrupt();
        assertThat(firstCarIdToReachTheEnd[0]).isEqualTo(calmCar.getCarId());
    }
}
