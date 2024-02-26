package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.dto.ShipmentInfoPageableResponse;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindAllShipmentsByTaxPayerRegistrationNoQuery {
    ShipmentRepository shipmentRepository;

    public FindAllShipmentsByTaxPayerRegistrationNoQuery(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public ShipmentInfoPageableResponse execute(String taxPayerRegistrationNo, Pageable pageable) {
        Page<ShipmentEntity> shipmentInfo = shipmentRepository.findAllByTaxPayerRegistrationNo(taxPayerRegistrationNo, pageable);
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
