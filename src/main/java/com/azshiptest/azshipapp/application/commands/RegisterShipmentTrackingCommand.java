package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import com.azshiptest.azshipapp.models.ShipmentInfo;
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

    public ShipmentInfo execute(ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentInfo shipmentInfo = buildShipmentInfo(shipmentInfoFormInput);
        this.shipmentInfoRepository.save(shipmentInfo);
        return shipmentInfo;
    }

    public ShipmentInfo buildShipmentInfo(ShipmentInfoFormInput shipmentInfoFormInput) {
        Address senderAddress = shipmentInfoFormInput.senderAddress();
        Address recipientAddress = shipmentInfoFormInput.recipientAddress();

        String trackingID = buildTrackingID(senderAddress, recipientAddress);

        LocalDate postingDate = LocalDate.now();
        LocalDate estimatedArrivalDate = postingDate.plusDays(calculateDaysToAdd(shipmentInfoFormInput.weight()));

        Optional<Integer> weight = shipmentInfoFormInput.weight();
        Optional<Float> cubingMeasurement = shipmentInfoFormInput.cubingMeasurement();

        return ShipmentInfo.builder()
                .taxPayerRegistrationNo(shipmentInfoFormInput.taxPayerRegistrationNo())
                .trackingID(trackingID)
                .senderAddress(senderAddress)
                .recipientAddress(recipientAddress)
                .shipmentStatus(ShipmentStatusEnum.POSTED)
                .postingDate(postingDate)
                .estimatedArrivalDate(estimatedArrivalDate)
                .value(shipmentInfoFormInput.value())
                .weight(weight.orElse(null))
                .cubingMeasurement(cubingMeasurement.orElse(null))
                .build();
    }

    private String buildTrackingID(Address senderAddress, Address recipientAddress) {
        String senderStateCode = senderAddress.getStateCodeEnum().toString();
        String recipientStateCode = recipientAddress.getStateCodeEnum().toString();

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
