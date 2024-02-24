package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.dto.ShipmentInfoPageableResponse;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindAllShipmentsByTaxPayerRegistrationNoQuery {
    ShipmentInfoRepository shipmentInfoRepository;

    public FindAllShipmentsByTaxPayerRegistrationNoQuery(ShipmentInfoRepository shipmentInfoRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
    }

    public ShipmentInfoPageableResponse execute(String taxPayerRegistrationNo, Pageable pageable) {
        Page<ShipmentInfo> shipmentInfo = shipmentInfoRepository.findAllByTaxPayerRegistrationNo(taxPayerRegistrationNo, pageable);
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
