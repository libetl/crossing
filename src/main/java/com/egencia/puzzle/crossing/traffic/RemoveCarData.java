package com.egencia.puzzle.crossing.traffic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RemoveCarData {
    private final UUID carId;

    @JsonCreator
    public RemoveCarData(@JsonProperty("carId") UUID carId) {
        this.carId = carId;
    }

    public UUID getCarId() {
        return carId;
    }
}
