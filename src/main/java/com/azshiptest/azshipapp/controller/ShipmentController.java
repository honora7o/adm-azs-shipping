package com.azshiptest.azshipapp.controller;

import com.azshiptest.azshipapp.application.commands.RegisterShipmentTrackingCommand;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {
    private final RegisterShipmentTrackingCommand registerShipmentTrackingCommand;

    public ShipmentController(RegisterShipmentTrackingCommand registerShipmentTrackingCommand) {
        this.registerShipmentTrackingCommand = registerShipmentTrackingCommand;
    }
    @PostMapping
    public ResponseEntity<String> save(@RequestBody ShipmentInfoFormInput shipmentInfoFormInput) {
        String trackingID = registerShipmentTrackingCommand.execute(shipmentInfoFormInput);
        return ResponseEntity.ok("Your tracking id is: " + trackingID);
    }
}
