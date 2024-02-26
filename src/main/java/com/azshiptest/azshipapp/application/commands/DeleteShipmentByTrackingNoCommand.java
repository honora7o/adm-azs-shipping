package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteShipmentByTrackingNoCommand {
    private final ShipmentRepository shipmentRepository;

    public DeleteShipmentByTrackingNoCommand(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public int execute(UUID trackingNo) {
        return shipmentRepository.deleteByTrackingNo(trackingNo);
    }
}
