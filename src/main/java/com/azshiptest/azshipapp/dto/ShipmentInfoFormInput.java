package com.azshiptest.azshipapp.dto;


import com.azshiptest.azshipapp.models.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.Optional;

public record ShipmentInfoFormInput(
        @NotNull
        @Pattern(regexp = "\\d{11}|\\d{14}", message = "Enter a valid CPF or CNPJ (only numbers)")
        String taxPayerRegistrationNo,

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
