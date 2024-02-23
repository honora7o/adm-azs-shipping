package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class UpdateShipmentInfoStatusByTrackingIDCommand {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public UpdateShipmentInfoStatusByTrackingIDCommand(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public int execute(String trackingID, ShipmentStatusEnum shipmentStatus) {
        return shipmentInfoRepository.updateShipmentStatusByTrackingID(trackingID, shipmentStatus);
    }
}
