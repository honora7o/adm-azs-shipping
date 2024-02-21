package com.azshiptest.azshipapp.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public record ShipmentInfo(
        Address senderAddress,
        Address recipientAddress,
        ShipmentStatusEnum shipmentStatus,
        LocalDate postingDate,
        LocalDate estimatedArrivalDate,
        BigDecimal value,
        Optional<Integer> weight,
        Optional<Float> cubingMeasurement
) {
}
