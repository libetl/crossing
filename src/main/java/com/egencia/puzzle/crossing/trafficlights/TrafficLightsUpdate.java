package com.egencia.puzzle.crossing.trafficlights;

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

    static class TrafficLightNewStatus {

        private final int trafficLightId;
        private final Side position;
        private final Status newStatus;

        @JsonCreator
        public TrafficLightNewStatus(@JsonProperty("trafficLightId") int trafficLightId,
                                     @JsonProperty("position") Side position,
                                     @JsonProperty("newStatus") Status newStatus) {
            this.trafficLightId = trafficLightId;
            this.position = position;
            this.newStatus = newStatus;
        }

        public Side getPosition() {
            return position;
        }

        public int getTrafficLightId() {
            return trafficLightId;
        }

        public Status getNewStatus() {
            return newStatus;
        }
    }

    enum Status {
        GREEN, ORANGE, RED
    }

}