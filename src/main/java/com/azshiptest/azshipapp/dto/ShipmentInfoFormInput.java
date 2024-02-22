package com.azshiptest.azshipapp.dto;


import com.azshiptest.azshipapp.infra.repositories.Address;

import java.math.BigDecimal;
import java.util.Optional;

public record ShipmentInfoFormInput(
        Address senderAddress,
        Address recipientAddress,
        BigDecimal value,
        Optional<Integer> weight,
        Optional<Float> cubingMeasurement
) {
}
