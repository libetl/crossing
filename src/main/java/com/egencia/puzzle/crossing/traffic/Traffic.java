package com.egencia.puzzle.crossing.traffic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class Traffic {

    private final List<Car> cars;

    @JsonCreator
    public Traffic(@JsonProperty("cars") List<Car> cars) {
        this.cars = Collections.unmodifiableList(cars);
    }

    public List<Car> getCars() {
        return cars;
    }
}
