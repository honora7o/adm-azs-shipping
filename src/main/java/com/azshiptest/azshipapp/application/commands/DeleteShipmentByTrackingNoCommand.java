package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeleteShipmentByTrackingNoCommand {
    private final ShipmentRepository shipmentRepository;

    public DeleteShipmentByTrackingNoCommand(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public Optional<String> execute(UUID trackingNo) {
        return shipmentRepository.deleteByTrackingNo(trackingNo) > 0 ?
                Optional.of("Shipment successfully deleted.") :
                Optional.empty();
    }
}
