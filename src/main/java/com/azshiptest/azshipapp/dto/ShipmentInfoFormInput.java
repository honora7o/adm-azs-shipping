package com.azshiptest.azshipapp.dto;


import com.azshiptest.azshipapp.models.Address;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public record ShipmentInfoFormInput(
        @NotNull
        Address senderAddress,

        @NotNull
        Address recipientAddress,

        @NotNull
        BigDecimal value,

        Optional<Integer> weight,
        Optional<Float> cubingMeasurement
) {
}
