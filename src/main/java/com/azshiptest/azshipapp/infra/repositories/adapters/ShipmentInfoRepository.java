package com.azshiptest.azshipapp.infra.repositories.adapters;

import com.azshiptest.azshipapp.infra.repositories.ports.ShipmentInfoRepositoryPort;
import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentInfoRepository extends JpaRepository<ShipmentInfo, String>, ShipmentInfoRepositoryPort {

    @Override
    ShipmentInfo save(ShipmentInfo shipmentInfo);

    @Override
    @Query("SELECT s FROM ShipmentInfo s " +
            "LEFT JOIN s.senderAddress sender " +
            "LEFT JOIN s.recipientAddress recipient " +
            "WHERE LOWER(s.trackingID) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
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
    Page<ShipmentInfo> universalSearchShipments(@Param("keyword") String keyword,
                                       Pageable pageable);

    @Override
    Optional<ShipmentInfo> findByTrackingID(String trackingID);
    @Override
    @Modifying
    @Query("UPDATE ShipmentInfo s SET s.shipmentStatus = :shipmentStatus WHERE s.trackingID = :trackingID")
    int updateShipmentStatusByTrackingID(@Param("trackingID") String trackingID, @Param("shipmentStatus") ShipmentStatusEnum shipmentStatus);

    @Override
    @Modifying
    @Query("UPDATE ShipmentInfo s SET s.recipientAddress = :newRecipientAddress WHERE s.trackingID = :trackingID")
    int updateShipmentRecipientAddressByTrackingID(@Param("trackingID") String trackingID, @Param("newRecipientAddress") Address newRecipientAddress);
}

