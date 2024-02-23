package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.dto.ShipmentInfoUniversalSearchQueryResponse;
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

    public ShipmentInfoUniversalSearchQueryResponse execute(String keyword, Pageable pageable) {
        Page<ShipmentInfo> shipmentInfo = shipmentInfoRepository.universalSearchShipments(keyword, pageable);
        ShipmentInfoUniversalSearchQueryResponse response = new ShipmentInfoUniversalSearchQueryResponse(
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
