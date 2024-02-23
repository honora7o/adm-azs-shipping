package com.azshiptest.azshipapp.infra.repositories;

import com.azshiptest.azshipapp.models.ShipmentInfo;

public interface ShipmentInfoRepositoryPort {
    ShipmentInfo save(ShipmentInfo shipmentInfo);
}
