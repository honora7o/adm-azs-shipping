package com.azshiptest.azshipapp.infra.repositories;

import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentInfoRepository extends JpaRepository<ShipmentInfo, String>, ShipmentInfoRepositoryPort {
    @Override
    ShipmentInfo save(ShipmentInfo shipmentInfo);
}
