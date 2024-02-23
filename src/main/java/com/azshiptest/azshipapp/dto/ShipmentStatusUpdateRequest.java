package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.models.ShipmentStatusEnum;

public record ShipmentStatusUpdateRequest(ShipmentStatusEnum shipmentStatusEnum) {
}
