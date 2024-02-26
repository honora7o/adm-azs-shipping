package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindShipmentByTrackingNoQuery {
    private final ShipmentRepository shipmentRepository;

    public FindShipmentByTrackingNoQuery(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Optional<ShipmentEntity> execute(UUID trackingNo) {
        return shipmentRepository.findByTrackingNo(trackingNo);
    }
}
