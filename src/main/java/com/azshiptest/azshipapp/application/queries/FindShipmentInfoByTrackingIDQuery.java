package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.infra.repositories.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.springframework.stereotype.Component;

@Component
public class FindShipmentInfoByTrackingIDQuery {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public FindShipmentInfoByTrackingIDQuery(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public ShipmentInfo execute(String trackingID) {
        return shipmentInfoRepository.findByTrackingID(trackingID);
    }
}
