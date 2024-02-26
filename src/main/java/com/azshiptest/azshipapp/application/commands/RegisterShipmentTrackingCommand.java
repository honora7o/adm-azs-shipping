package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Component
public class RegisterShipmentTrackingCommand {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public RegisterShipmentTrackingCommand(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public ShipmentEntity execute(ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentEntity shipmentEntity = buildShipmentInfo(shipmentInfoFormInput);
        this.shipmentInfoRepository.save(shipmentEntity);
        return shipmentEntity;
    }

    public ShipmentEntity buildShipmentInfo(ShipmentInfoFormInput shipmentInfoFormInput) {
        AddressEntity senderAddressEntity = shipmentInfoFormInput.senderAddressEntity();
        AddressEntity recipientAddressEntity = shipmentInfoFormInput.recipientAddressEntity();

        String trackingID = buildTrackingID(senderAddressEntity, recipientAddressEntity);

        LocalDate postingDate = LocalDate.now();
        LocalDate estimatedArrivalDate = postingDate.plusDays(calculateDaysToAdd(shipmentInfoFormInput.weight()));

        Optional<Integer> weight = shipmentInfoFormInput.weight();
        Optional<Float> cubingMeasurement = shipmentInfoFormInput.cubingMeasurement();

        return ShipmentEntity.builder()
                .taxPayerRegistrationNo(shipmentInfoFormInput.taxPayerRegistrationNo())
                .trackingID(trackingID)
                .senderAddressEntity(senderAddressEntity)
                .recipientAddressEntity(recipientAddressEntity)
                .shipmentStatus(ShipmentStatusEnum.POSTED)
                .postingDate(postingDate)
                .estimatedArrivalDate(estimatedArrivalDate)
                .value(shipmentInfoFormInput.value())
                .weight(weight.orElse(null))
                .cubingMeasurement(cubingMeasurement.orElse(null))
                .build();
    }

    private String buildTrackingID(AddressEntity senderAddressEntity, AddressEntity recipientAddressEntity) {
        String senderStateCode = senderAddressEntity.getStateCodeEnum().toString();
        String recipientStateCode = recipientAddressEntity.getStateCodeEnum().toString();

        StringBuilder trackingIDBuilder = new StringBuilder();
        trackingIDBuilder.append(senderStateCode);

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            trackingIDBuilder.append(random.nextInt(10));
        }

        trackingIDBuilder.append(recipientStateCode);
        return trackingIDBuilder.toString();
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
