package com.azshiptest.azshipapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public record ShipmentInfoFormInput(
        Address senderAddress,
        Address recipientAddress,
        LocalDate postingDate,
        BigDecimal value,
        Optional<Integer> weight,
        Optional<Float> cubingMeasurement
) {
}
