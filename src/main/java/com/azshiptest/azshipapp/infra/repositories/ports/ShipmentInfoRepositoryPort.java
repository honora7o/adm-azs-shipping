package com.azshiptest.azshipapp.infra.repositories.ports;

import com.azshiptest.azshipapp.models.ShipmentInfo;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShipmentInfoRepositoryPort {
    ShipmentInfo save(ShipmentInfo shipmentInfo);
    Optional<ShipmentInfo> findByTrackingID(String trackingID);
    Page<ShipmentInfo> universalSearchShipments(@Param("keyword") String keyword,
                                                Pageable pageable);
    int updateShipmentStatusByTrackingID(@Param("trackingID") String trackingID, @Param("shipmentStatus") ShipmentStatusEnum shipmentStatus);
    int deleteByTrackingID(String trackingID);
    Page<ShipmentInfo> findAllByTaxPayerRegistrationNo(String taxPayerRegistrationNo, Pageable pageable);
}
