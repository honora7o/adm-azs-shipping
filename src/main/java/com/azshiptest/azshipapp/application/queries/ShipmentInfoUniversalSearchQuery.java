package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.infra.repositories.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ShipmentInfoUniversalSearchQuery {
    private final ShipmentInfoRepository shipmentInfoRepository;

    public ShipmentInfoUniversalSearchQuery(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public Page<ShipmentInfo> execute(String keyword, Pageable pageable) {
        return shipmentInfoRepository.universalSearchShipments(keyword, pageable);
    }
}
