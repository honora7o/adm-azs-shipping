package com.azshiptest.azshipapp.controller;

import com.azshiptest.azshipapp.application.commands.RegisterShipmentTrackingCommand;
import com.azshiptest.azshipapp.application.queries.ShippingInfoUniversalSearchQuery;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {
    private final RegisterShipmentTrackingCommand registerShipmentTrackingCommand;
    private final ShippingInfoUniversalSearchQuery shippingInfoUniversalSearchQuery;

    public ShipmentController(RegisterShipmentTrackingCommand registerShipmentTrackingCommand,
                              ShippingInfoUniversalSearchQuery shippingInfoUniversalSearchQuery) {
        this.registerShipmentTrackingCommand = registerShipmentTrackingCommand;
        this.shippingInfoUniversalSearchQuery = shippingInfoUniversalSearchQuery;
    }
    @PostMapping
    public ResponseEntity<ShipmentInfo> save(@RequestBody ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentInfo shipmentInfo = registerShipmentTrackingCommand.execute(shipmentInfoFormInput);
        return ResponseEntity.ok(shipmentInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ShipmentInfo>> searchShipments(@RequestParam String keyword,
                                                              @RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "3") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ShipmentInfo> shipments = shippingInfoUniversalSearchQuery.execute(keyword, pageable);
        return ResponseEntity.ok(shipments);
    }
}
