package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.dto.Address;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.dto.ShipmentStatusEnum;
import com.azshiptest.azshipapp.infra.repositories.ShipmentInfo;
import com.azshiptest.azshipapp.infra.repositories.ShipmentInfoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Component
public class RegisterShipmentTrackingCommand {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public RegisterShipmentTrackingCommand(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public void execute(ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentInfo shipmentInfo = buildShipmentInfo(shipmentInfoFormInput);
        this.shipmentInfoRepository.save(shipmentInfo);
    }

    public ShipmentInfo buildShipmentInfo(ShipmentInfoFormInput shipmentInfoFormInput) {
        Address senderAddress = shipmentInfoFormInput.senderAddress();
        Address recipientAddress = shipmentInfoFormInput.recipientAddress();

        String trackingID = buildTrackingID(senderAddress, recipientAddress);

        LocalDate postingDate = shipmentInfoFormInput.postingDate();
        LocalDate estimatedArrivalDate = postingDate.plusDays(calculateDaysToAdd(shipmentInfoFormInput.weight()));

        Optional<Integer> weight = shipmentInfoFormInput.weight();
        Optional<Float> cubingMeasurement = shipmentInfoFormInput.cubingMeasurement();

        return new ShipmentInfo.ShipmentInfoBuilder()
                .withTrackingID(trackingID)
                .withSenderAddress(senderAddress)
                .withRecipientAddress(recipientAddress)
                .withShipmentStatus(generateRandomShipmentStatus())
                .withPostingDate(postingDate)
                .withEstimatedArrivalDate(estimatedArrivalDate)
                .withValue(shipmentInfoFormInput.value())
                .withWeight(weight.orElse(null))
                .withCubingMeasurement(cubingMeasurement.orElse(null))
                .build();

    }

    private String buildTrackingID(Address senderAddress, Address recipientAddress) {
        String senderStateCode = senderAddress.stateCodeEnum().toString();
        String recipientStateCode = recipientAddress.stateCodeEnum().toString();

        StringBuilder trackingIDBuilder = new StringBuilder();
        trackingIDBuilder.append(senderStateCode);

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            trackingIDBuilder.append(random.nextInt(10));
        }

        trackingIDBuilder.append(recipientStateCode);
        return trackingIDBuilder.toString();
    }

    // this implementation is for testing purposes only, as correct status on first build would always be "POSTED"
    // and actually updating it correctly would probably be handled by a different microservice
    private ShipmentStatusEnum generateRandomShipmentStatus() {
        Random random = new Random();
        int index = random.nextInt(ShipmentStatusEnum.values().length);
        return ShipmentStatusEnum.values()[index];
    }

    // mock implementation where days to ship is calculated on cargo weight
    private int calculateDaysToAdd(Optional<Integer> weight) {
        if (weight.isPresent()) {
            int actualWeight = weight.get();
            if (actualWeight >= 100) {
                return 5; // if weight is 100 or more, add 5 days
            } else if (actualWeight >= 50) {
                return 3; // if weight is between 50 and 99, add 3 days
            } else {
                return 1; // if weight is less than 50, add 1 day
            }
        } else {
            return 10;
        }
    }
}
