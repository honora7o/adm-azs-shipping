package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class RegisterShipmentTrackingCommand {
    private final ShipmentRepository shipmentRepository;

    public RegisterShipmentTrackingCommand(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public ShipmentEntity execute(ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentEntity shipmentEntity = buildShipmentInfo(shipmentInfoFormInput);
        this.shipmentRepository.save(shipmentEntity);
        return shipmentEntity;
    }

    public ShipmentEntity buildShipmentInfo(ShipmentInfoFormInput shipmentInfoFormInput) {
        AddressEntity senderAddressEntity = shipmentInfoFormInput.senderAddress();
        AddressEntity recipientAddressEntity = shipmentInfoFormInput.recipientAddress();

        LocalDate postingDate = LocalDate.now();
        LocalDate estimatedArrivalDate = postingDate.plusDays(calculateDaysToAdd(shipmentInfoFormInput.weight()));

        Optional<Integer> weight = shipmentInfoFormInput.weight();
        Optional<Float> cubingMeasurement = shipmentInfoFormInput.cubingMeasurement();

        return ShipmentEntity.builder()
                .taxPayerRegistrationNo(shipmentInfoFormInput.taxPayerRegistrationNo())
                .senderAddress(senderAddressEntity)
                .recipientAddress(recipientAddressEntity)
                .shipmentStatus(ShipmentStatusEnum.POSTED)
                .postingDate(postingDate)
                .estimatedArrivalDate(estimatedArrivalDate)
                .value(shipmentInfoFormInput.value())
                .weight(weight.orElse(null))
                .cubingMeasurement(cubingMeasurement.orElse(null))
                .build();
    }

    // mock implementation where days to ship is calculated on cargo weight
    private int calculateDaysToAdd(Optional<Integer> weight) {
        if (weight.isPresent()) {
            int actualWeight = weight.get();
            if (actualWeight >= 100) {
                return 5;
            } else if (actualWeight >= 50) {
                return 3;
            } else {
                return 1;
            }
        } else {
            return 10;
        }
    }
}
