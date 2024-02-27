package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.models.ShipmentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ShipmentDTO(UUID trackingNo,
                          String taxPayerRegistrationNo,
                          AddressDTO senderAddress,
                          AddressDTO recipientAddress,
                          ShipmentStatusEnum shipmentStatus,
                          LocalDate postingDate,
                          LocalDate estimatedArrivalDate,
                          BigDecimal value,
                          Integer weight,
                          Float cubingMeasurement
) {
}
