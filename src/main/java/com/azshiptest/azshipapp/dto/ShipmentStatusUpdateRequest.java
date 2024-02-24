package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import jakarta.validation.constraints.NotNull;

public record ShipmentStatusUpdateRequest(@NotNull ShipmentStatusEnum shipmentStatusEnum) {
}
