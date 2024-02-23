package com.azshiptest.azshipapp.controller;

import com.azshiptest.azshipapp.application.commands.RegisterShipmentTrackingCommand;
import com.azshiptest.azshipapp.application.commands.UpdateShipmentInfoStatusByTrackingIDCommand;
import com.azshiptest.azshipapp.application.queries.FindShipmentInfoByTrackingIDQuery;
import com.azshiptest.azshipapp.application.queries.ShipmentInfoUniversalSearchQuery;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.dto.ShipmentInfoUniversalSearchQueryResponse;
import com.azshiptest.azshipapp.dto.ShipmentStatusUpdateRequest;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {
    private final RegisterShipmentTrackingCommand registerShipmentTrackingCommand;
    private final ShipmentInfoUniversalSearchQuery shipmentInfoUniversalSearchQuery;
    private final FindShipmentInfoByTrackingIDQuery findShipmentInfoByTrackingIDQuery;
    private final UpdateShipmentInfoStatusByTrackingIDCommand updateShipmentInfoStatusByTrackingIDCommand;

    public ShipmentController(RegisterShipmentTrackingCommand registerShipmentTrackingCommand,
                              ShipmentInfoUniversalSearchQuery shipmentInfoUniversalSearchQuery,
                              FindShipmentInfoByTrackingIDQuery findShipmentInfoByTrackingIDQuery,
                              UpdateShipmentInfoStatusByTrackingIDCommand updateShipmentInfoStatusByTrackingIDCommand) {
        this.registerShipmentTrackingCommand = registerShipmentTrackingCommand;
        this.shipmentInfoUniversalSearchQuery = shipmentInfoUniversalSearchQuery;
        this.findShipmentInfoByTrackingIDQuery = findShipmentInfoByTrackingIDQuery;
        this.updateShipmentInfoStatusByTrackingIDCommand = updateShipmentInfoStatusByTrackingIDCommand;
    }
    @PostMapping
    public ResponseEntity<ShipmentInfo> save(@RequestBody ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentInfo shipmentInfo = registerShipmentTrackingCommand.execute(shipmentInfoFormInput);
        return ResponseEntity.ok(shipmentInfo);
    }

    @GetMapping("/{trackingID}")
    public ResponseEntity<ShipmentInfo> findShipmentInfoByTrackingID(@PathVariable String trackingID) {
        ShipmentInfo shipmentInfo = findShipmentInfoByTrackingIDQuery.execute(trackingID);
        return ResponseEntity.ok(shipmentInfo);
    }

    @GetMapping("/search")
    public ResponseEntity<ShipmentInfoUniversalSearchQueryResponse> searchShipments(@RequestParam String keyword,
                                                                                    @RequestParam(defaultValue = "0") int pageNo,
                                                                                    @RequestParam(defaultValue = "3") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        ShipmentInfoUniversalSearchQueryResponse shipments = shipmentInfoUniversalSearchQuery.execute(keyword, pageable);
        return ResponseEntity.ok(shipments);
    }

    @PutMapping("/{trackingID}")
    @Transactional
    public ResponseEntity<?> updateShipmentInfoStatusByTrackingID(@PathVariable String trackingID,
                                                                             @RequestBody ShipmentStatusUpdateRequest request) {
        ShipmentStatusEnum newShipmentStatus = request.shipmentStatusEnum();
        return (updateShipmentInfoStatusByTrackingIDCommand.execute(trackingID, newShipmentStatus) > 0) ?
                ResponseEntity.ok("Shipment status for the shipment of tracking ID " + trackingID + " has been successfully updated!") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking ID " + trackingID + " not found");
    }
}
