package com.egencia.puzzle.crossing.trafficlights;

import com.egencia.puzzle.crossing.position.Position;
import com.egencia.puzzle.crossing.position.Side;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class TrafficLightsUpdate {

    private final List<TrafficLightNewStatus> newStatuses;

    @JsonCreator
    public TrafficLightsUpdate(@JsonProperty("newStatuses") List<TrafficLightNewStatus> newStatuses) {
        this.newStatuses = unmodifiableList(newStatuses);
    }

    public List<TrafficLightNewStatus> getNewStatuses() {
        return newStatuses;
    }

    public static class TrafficLightNewStatus {

        private final Side side;
        private final Status newStatus;

        @JsonCreator
        public TrafficLightNewStatus(@JsonProperty("side") Side side,
                                     @JsonProperty("newStatus") Status newStatus) {
            this.side = side;
            this.newStatus = newStatus;
        }

        public Side getSide() {
            return side;
        }

        public Position getPosition (){
            return side.asVector(10);
        }

        public Status getNewStatus() {
            return newStatus;
        }
    }

    public enum Status {
        GREEN, ORANGE, RED
    }

}