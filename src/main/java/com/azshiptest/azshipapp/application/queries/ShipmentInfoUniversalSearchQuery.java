package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.dto.ShipmentInfoPageableResponse;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ShipmentInfoUniversalSearchQuery {
    private final ShipmentRepository shipmentRepository;

    public ShipmentInfoUniversalSearchQuery(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public ShipmentInfoPageableResponse execute(String keyword, Pageable pageable) {
        Page<ShipmentEntity> shipmentInfo = shipmentRepository.universalSearchShipments(keyword, pageable);
        ShipmentInfoPageableResponse response = new ShipmentInfoPageableResponse(
                shipmentInfo.getContent(),
                shipmentInfo.getPageable().getPageNumber(),
                shipmentInfo.getPageable().getPageSize(),
                shipmentInfo.getTotalElements(),
                shipmentInfo.getTotalPages(),
                shipmentInfo.isLast()
        );

        return response;
    }
}
