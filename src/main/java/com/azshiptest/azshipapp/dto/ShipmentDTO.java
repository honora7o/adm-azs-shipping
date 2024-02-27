package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public static ShipmentDTO fromEntity(ShipmentEntity entity) {
        return new ShipmentDTO(
                entity.getTrackingNo(),
                entity.getTaxPayerRegistrationNo(),
                AddressDTO.fromEntity(entity.getSenderAddress()),
                AddressDTO.fromEntity(entity.getRecipientAddress()),
                entity.getShipmentStatus(),
                entity.getPostingDate(),
                entity.getEstimatedArrivalDate(),
                entity.getValue(),
                entity.getWeight(),
                entity.getCubingMeasurement()
        );
    }

    public static List<ShipmentDTO> mapEntitiesToDTOs(List<ShipmentEntity> entities) {
        return entities.stream()
                .map(ShipmentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
