package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class UpdateShipmentStatusByTrackingIDCommand {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public UpdateShipmentStatusByTrackingIDCommand(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public int execute(String trackingID, ShipmentStatusEnum shipmentStatus) {
        return shipmentInfoRepository.updateShipmentStatusByTrackingID(trackingID, shipmentStatus);
    }
}
