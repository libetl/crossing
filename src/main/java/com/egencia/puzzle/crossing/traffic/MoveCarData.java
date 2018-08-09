package com.egencia.puzzle.crossing.traffic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class MoveCarData {
    private final UUID carId;
    private final Situation newSituation;

    @JsonCreator
    public MoveCarData(@JsonProperty("carId") UUID carId, @JsonProperty("newSituation") Situation newSituation) {
        this.carId = carId;
        this.newSituation = newSituation;
    }

    public UUID getCarId() {
        return carId;
    }

    public Situation getNewSituation() {
        return newSituation;
    }
}
