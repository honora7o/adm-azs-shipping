package com.azshiptest.azshipapp.controller;

import com.azshiptest.azshipapp.application.commands.DeleteShipmentByTrackingIDCommand;
import com.azshiptest.azshipapp.application.commands.RegisterShipmentTrackingCommand;
import com.azshiptest.azshipapp.application.commands.UpdateShipmentRecipientAddressByTrackingIDCommand;
import com.azshiptest.azshipapp.application.commands.UpdateShipmentStatusByTrackingIDCommand;
import com.azshiptest.azshipapp.application.queries.FindAllShipmentsByTaxPayerRegistrationNoQuery;
import com.azshiptest.azshipapp.application.queries.FindShipmentByTrackingIDQuery;
import com.azshiptest.azshipapp.application.queries.ShipmentInfoUniversalSearchQuery;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.dto.ShipmentInfoPageableResponse;
import com.azshiptest.azshipapp.dto.ShipmentStatusUpdateRequest;
import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {
    private final RegisterShipmentTrackingCommand registerShipmentTrackingCommand;
    private final ShipmentInfoUniversalSearchQuery shipmentInfoUniversalSearchQuery;
    private final FindShipmentByTrackingIDQuery findShipmentByTrackingIDQuery;
    private final UpdateShipmentStatusByTrackingIDCommand updateShipmentStatusByTrackingIDCommand;
    private final UpdateShipmentRecipientAddressByTrackingIDCommand updateShipmentRecipientAddressByTrackingIDCommand;
    private final DeleteShipmentByTrackingIDCommand deleteShipmentInfoByTrackingIDCommand;
    private final FindAllShipmentsByTaxPayerRegistrationNoQuery findAllShipmentsByTaxPayerRegistrationNoQuery;

    public ShipmentController(RegisterShipmentTrackingCommand registerShipmentTrackingCommand,
                              ShipmentInfoUniversalSearchQuery shipmentInfoUniversalSearchQuery,
                              FindShipmentByTrackingIDQuery findShipmentByTrackingIDQuery,
                              UpdateShipmentStatusByTrackingIDCommand updateShipmentStatusByTrackingIDCommand,
                              UpdateShipmentRecipientAddressByTrackingIDCommand updateShipmentRecipientAddressByTrackingIDCommand,
                              DeleteShipmentByTrackingIDCommand deleteShipmentInfoByTrackingIDCommand,
                              FindAllShipmentsByTaxPayerRegistrationNoQuery findAllShipmentsByTaxPayerRegistrationNoQuery) {
        this.registerShipmentTrackingCommand = registerShipmentTrackingCommand;
        this.shipmentInfoUniversalSearchQuery = shipmentInfoUniversalSearchQuery;
        this.findShipmentByTrackingIDQuery = findShipmentByTrackingIDQuery;
        this.updateShipmentStatusByTrackingIDCommand = updateShipmentStatusByTrackingIDCommand;
        this.updateShipmentRecipientAddressByTrackingIDCommand = updateShipmentRecipientAddressByTrackingIDCommand;
        this.deleteShipmentInfoByTrackingIDCommand = deleteShipmentInfoByTrackingIDCommand;
        this.findAllShipmentsByTaxPayerRegistrationNoQuery = findAllShipmentsByTaxPayerRegistrationNoQuery;
    }
    @PostMapping
    public ResponseEntity<ShipmentInfo> save(@RequestBody @Valid ShipmentInfoFormInput shipmentInfoFormInput) {
        ShipmentInfo shipmentInfo = registerShipmentTrackingCommand.execute(shipmentInfoFormInput);
        return ResponseEntity.ok(shipmentInfo);
    }

    @GetMapping("/tracking-id/{trackingID}")
    public ResponseEntity<ShipmentInfo> findShipmentInfoByTrackingID(@PathVariable String trackingID) {
        Optional<ShipmentInfo> shipmentInfo = findShipmentByTrackingIDQuery.execute(trackingID);
        return ResponseEntity.ok(shipmentInfo.get());
    }

    @GetMapping("/tax-payer-registration-number/{taxPayerRegistrationNo}")
    public ResponseEntity<ShipmentInfoPageableResponse> findAllShipmentByTaxPayerRegistrationNo(@PathVariable String taxPayerRegistrationNo,
                                                                                                @RequestParam(defaultValue = "0") int pageNo,
                                                                                                @RequestParam(defaultValue = "3") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        ShipmentInfoPageableResponse shipments = findAllShipmentsByTaxPayerRegistrationNoQuery.execute(taxPayerRegistrationNo, pageable);
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/search")
    public ResponseEntity<ShipmentInfoPageableResponse> searchShipments(@RequestParam String keyword,
                                                                        @RequestParam(defaultValue = "0") int pageNo,
                                                                        @RequestParam(defaultValue = "3") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        ShipmentInfoPageableResponse shipments = shipmentInfoUniversalSearchQuery.execute(keyword, pageable);
        return ResponseEntity.ok(shipments);
    }

    @PutMapping("/update-status/{trackingID}")
    @Transactional
    public ResponseEntity<?> updateShipmentInfoStatusByTrackingID(@PathVariable String trackingID,
                                                                             @RequestBody ShipmentStatusUpdateRequest request) {
        ShipmentStatusEnum newShipmentStatus = request.shipmentStatusEnum();
        return (updateShipmentStatusByTrackingIDCommand.execute(trackingID, newShipmentStatus) > 0) ?
                ResponseEntity.ok("Shipment status for the shipment of tracking ID " + trackingID + " has been successfully updated!") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking ID " + trackingID + " not found");
    }

    @PutMapping("/update-recipient-address/{trackingID}")
    @Transactional
    public ResponseEntity<?> updateShipmentInfoRecipientAddress(@PathVariable String trackingID,
                                                                  @RequestBody Address newRecipientAddress) {
        return (updateShipmentRecipientAddressByTrackingIDCommand.execute(trackingID, newRecipientAddress) > 0) ?
                ResponseEntity.ok("Recipient address for the shipment of tracking ID " + trackingID + " has been successfully updated!") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking ID " + trackingID + " not found");
    }

    @DeleteMapping("/delete/{trackingID}")
    public ResponseEntity<?> deleteShipmentInfoByTrackingID(@PathVariable String trackingID) {
        return (deleteShipmentInfoByTrackingIDCommand.execute(trackingID) > 0) ?
                ResponseEntity.ok("Shipment info for the shipment of tracking ID " + trackingID + " has been successfully deleted.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking ID " + trackingID + " not found");
    }
}
