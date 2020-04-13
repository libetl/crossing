package com.egencia.puzzle.crossing.traffic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class CurrentTraffic {
    private static final AtomicReference<Map<UUID, Car>> TRAFFIC = new AtomicReference<>(new HashMap<>());

    synchronized static Traffic getTraffic() {
        synchronized (TRAFFIC) {
            return new Traffic(new ArrayList<>(TRAFFIC.get().values()));
        }
    }

    public static synchronized void newCar(Car car) {
        synchronized (TRAFFIC) {
            TRAFFIC.get().put(car.getCarId(), car);
        }
    }

    public static synchronized void moveCar(MoveCarData moveCarData) {
        synchronized (TRAFFIC) {
            if (TRAFFIC.get().get(moveCarData.getCarId()) != null)
                TRAFFIC.get().put(moveCarData.getCarId(),
                        TRAFFIC.get().get(moveCarData.getCarId()).with(moveCarData.getNewSituation()));
        }
    }

    public static synchronized void removeCar(RemoveCarData removeCarData) {
        synchronized (TRAFFIC) {
            TRAFFIC.get().remove(removeCarData.getCarId());
        }
    }

    public static void clear() {
        TRAFFIC.get().clear();
    }
}
