package com.azshiptest.azshipapp.infra.repositories.ports;

import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepositoryPort {
    ShipmentEntity save(ShipmentEntity shipmentEntity);
    Optional<ShipmentEntity> findByTrackingNo(UUID trackingNo);
    Page<ShipmentEntity> universalSearchShipments(@Param("keyword") String keyword,
                                                  Pageable pageable);
    int updateShipmentStatusByTrackingNo(@Param("trackingNo") UUID trackingNo, @Param("shipmentStatus") ShipmentStatusEnum shipmentStatus);
    int deleteByTrackingNo(UUID trackingNo);
    Page<ShipmentEntity> findAllByTaxPayerRegistrationNo(String taxPayerRegistrationNo, Pageable pageable);
}
