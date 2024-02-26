package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ShipmentInfoPageableResponse {
    private List<ShipmentEntity> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
