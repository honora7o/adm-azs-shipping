package com.azshiptest.azshipapp.infra.repositories.adapters;

import com.azshiptest.azshipapp.infra.repositories.ports.ShipmentRepositoryPort;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID>, ShipmentRepositoryPort {

    @Override
    ShipmentEntity save(ShipmentEntity shipmentEntity);

    @Override
    int deleteByTrackingNo(UUID trackingNo);

    @Override
    Page<ShipmentEntity> findAllByTaxPayerRegistrationNo(String taxPayerRegistrationNo, Pageable pageable);

    @Override
    @Query("SELECT s FROM ShipmentEntity s " +
            "LEFT JOIN s.senderAddress sender " +
            "LEFT JOIN s.recipientAddress recipient " +
            "WHERE LOWER(CAST(s.trackingNo AS text)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(sender.streetName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(sender.neighbourhood) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(sender.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(CAST(sender.stateCodeEnum AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(sender.zipCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(recipient.streetName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(recipient.neighbourhood) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(recipient.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(CAST(recipient.stateCodeEnum AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(recipient.zipCode) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ShipmentEntity> universalSearchShipments(@Param("keyword") String keyword,
                                                  Pageable pageable);

    @Override
    Optional<ShipmentEntity> findByTrackingNo(UUID trackingNo);
    @Override
    @Modifying
    @Query("UPDATE ShipmentEntity s SET s.shipmentStatus = :shipmentStatus WHERE s.trackingNo = :trackingNo")
    int updateShipmentStatusByTrackingNo(@Param("trackingNo") UUID trackingNo, @Param("shipmentStatus") ShipmentStatusEnum shipmentStatus);
}

