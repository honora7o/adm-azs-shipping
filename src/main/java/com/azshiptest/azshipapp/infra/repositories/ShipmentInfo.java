package com.azshiptest.azshipapp.infra.repositories;

import com.azshiptest.azshipapp.dto.Address;
import com.azshiptest.azshipapp.dto.ShipmentStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public record ShipmentInfo(
        @Id
        String trackingID,
        Address senderAddress,
        Address recipientAddress,
        ShipmentStatusEnum shipmentStatus,
        LocalDate postingDate,
        LocalDate estimatedArrivalDate,
        BigDecimal value,
        Optional<Integer> weight,
        Optional<Float> cubingMeasurement
) {
    public static class ShipmentInfoBuilder {
        private String trackingID;
        private Address senderAddress;
        private Address recipientAddress;
        private ShipmentStatusEnum shipmentStatus;
        private LocalDate postingDate;
        private LocalDate estimatedArrivalDate;
        private BigDecimal value;
        private Optional<Integer> weight = Optional.empty();
        private Optional<Float> cubingMeasurement = Optional.empty();

        public ShipmentInfoBuilder withTrackingID(String trackingID) {
            this.trackingID = trackingID;
            return this;
        }

        public ShipmentInfoBuilder withSenderAddress(Address senderAddress) {
            this.senderAddress = senderAddress;
            return this;
        }

        public ShipmentInfoBuilder withRecipientAddress(Address recipientAddress) {
            this.recipientAddress = recipientAddress;
            return this;
        }

        public ShipmentInfoBuilder withShipmentStatus(ShipmentStatusEnum shipmentStatus) {
            this.shipmentStatus = shipmentStatus;
            return this;
        }

        public ShipmentInfoBuilder withPostingDate(LocalDate postingDate) {
            this.postingDate = postingDate;
            return this;
        }

        public ShipmentInfoBuilder withEstimatedArrivalDate(LocalDate postingDate) {
            this.estimatedArrivalDate = estimatedArrivalDate;
            return this;
        }

        public ShipmentInfoBuilder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public ShipmentInfoBuilder withWeight(Integer weight) {
            this.weight = Optional.of(weight);
            return this;
        }

        public ShipmentInfoBuilder withCubingMeasurement(Float cubingMeasurement) {
            this.cubingMeasurement = Optional.of(cubingMeasurement);
            return this;
        }

        public ShipmentInfo build() {
            return new ShipmentInfo(trackingID, senderAddress, recipientAddress, shipmentStatus, postingDate,
                    estimatedArrivalDate, value, weight, cubingMeasurement);
        }
    }
}


