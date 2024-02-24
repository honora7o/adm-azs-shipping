package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.models.ShipmentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ShipmentInfoPageableResponse {
    private List<ShipmentInfo> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
