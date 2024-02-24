package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DeleteShipmentByTrackingIDCommand {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public DeleteShipmentByTrackingIDCommand(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    @Transactional
    public int execute(String trackingID) {
        return shipmentInfoRepository.deleteByTrackingID(trackingID);
    }
}
