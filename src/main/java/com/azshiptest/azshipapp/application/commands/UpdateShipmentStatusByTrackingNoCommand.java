package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateShipmentStatusByTrackingNoCommand {
    private final ShipmentRepository shipmentRepository;

    public UpdateShipmentStatusByTrackingNoCommand(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public Optional<String> execute(UUID trackingNo, ShipmentStatusEnum shipmentStatus) {
        return shipmentRepository.updateShipmentStatusByTrackingNo(trackingNo, shipmentStatus) > 0 ?
                Optional.of("Shipment status successfully updated.") :
                Optional.empty();
    }
}
