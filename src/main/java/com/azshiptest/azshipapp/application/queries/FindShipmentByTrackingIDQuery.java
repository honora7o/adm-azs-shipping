package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindShipmentByTrackingIDQuery {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public FindShipmentByTrackingIDQuery(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public Optional<ShipmentInfo> execute(String trackingID) {
        return shipmentInfoRepository.findByTrackingID(trackingID);
    }
}
