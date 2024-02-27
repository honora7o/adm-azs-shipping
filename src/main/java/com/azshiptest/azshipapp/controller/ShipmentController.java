package com.azshiptest.azshipapp.controller;

import com.azshiptest.azshipapp.application.commands.DeleteShipmentByTrackingNoCommand;
import com.azshiptest.azshipapp.application.commands.RegisterShipmentTrackingCommand;
import com.azshiptest.azshipapp.application.commands.UpdateShipmentRecipientAddressByTrackingNoCommand;
import com.azshiptest.azshipapp.application.commands.UpdateShipmentStatusByTrackingNoCommand;
import com.azshiptest.azshipapp.application.queries.FindAllShipmentsByTaxPayerRegistrationNoQuery;
import com.azshiptest.azshipapp.application.queries.FindShipmentByTrackingNoQuery;
import com.azshiptest.azshipapp.application.queries.ShipmentInfoUniversalSearchQuery;
import com.azshiptest.azshipapp.dto.*;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {
    private final RegisterShipmentTrackingCommand registerShipmentTrackingCommand;
    private final ShipmentInfoUniversalSearchQuery shipmentInfoUniversalSearchQuery;
    private final FindShipmentByTrackingNoQuery findShipmentByTrackingNoQuery;
    private final UpdateShipmentStatusByTrackingNoCommand updateShipmentStatusByTrackingNoCommand;
    private final UpdateShipmentRecipientAddressByTrackingNoCommand updateShipmentRecipientAddressByTrackingNoCommand;
    private final DeleteShipmentByTrackingNoCommand deleteShipmentByTrackingNoCommand;
    private final FindAllShipmentsByTaxPayerRegistrationNoQuery findAllShipmentsByTaxPayerRegistrationNoQuery;

    public ShipmentController(RegisterShipmentTrackingCommand registerShipmentTrackingCommand,
                              ShipmentInfoUniversalSearchQuery shipmentInfoUniversalSearchQuery,
                              FindShipmentByTrackingNoQuery findShipmentByTrackingNoQuery,
                              UpdateShipmentStatusByTrackingNoCommand updateShipmentStatusByTrackingNoCommand,
                              UpdateShipmentRecipientAddressByTrackingNoCommand updateShipmentRecipientAddressByTrackingNoCommand,
                              DeleteShipmentByTrackingNoCommand deleteShipmentInfoByTrackingNoCommand,
                              FindAllShipmentsByTaxPayerRegistrationNoQuery findAllShipmentsByTaxPayerRegistrationNoQuery) {
        this.registerShipmentTrackingCommand = registerShipmentTrackingCommand;
        this.shipmentInfoUniversalSearchQuery = shipmentInfoUniversalSearchQuery;
        this.findShipmentByTrackingNoQuery = findShipmentByTrackingNoQuery;
        this.updateShipmentStatusByTrackingNoCommand = updateShipmentStatusByTrackingNoCommand;
        this.updateShipmentRecipientAddressByTrackingNoCommand = updateShipmentRecipientAddressByTrackingNoCommand;
        this.deleteShipmentByTrackingNoCommand = deleteShipmentInfoByTrackingNoCommand;
        this.findAllShipmentsByTaxPayerRegistrationNoQuery = findAllShipmentsByTaxPayerRegistrationNoQuery;
    }
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ShipmentInfoFormInput shipmentInfoFormInput,
                                             UriComponentsBuilder uriBuilder) {
        ShipmentEntity shipmentEntity = registerShipmentTrackingCommand.execute(shipmentInfoFormInput);
        URI location = uriBuilder.path("/api/shipments/{trackingNo}")
                .buildAndExpand(shipmentEntity.getTrackingNo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{trackingNo}")
    public ResponseEntity<ShipmentDTO> findShipmentInfoByTrackingNo(@PathVariable UUID trackingNo) {
        Optional<ShipmentDTO> shipmentInfo = findShipmentByTrackingNoQuery.execute(trackingNo);
        return shipmentInfo
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<ShipmentInfoPageableResponse> findAllShipmentByTaxPayerRegistrationNo(@RequestParam String taxPayerRegistrationNo,
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

    @PatchMapping("/{trackingNo}/status")
    @Transactional
    public ResponseEntity<String> updateShipmentInfoStatusByTrackingNo(@PathVariable UUID trackingNo,
                                                                       @RequestBody ShipmentStatusUpdateRequest request) {
        ShipmentStatusEnum newShipmentStatus = request.shipmentStatusEnum();
        Optional<String> updateResult = updateShipmentStatusByTrackingNoCommand.execute(trackingNo, newShipmentStatus);
        return updateResult
                .map(result -> ResponseEntity.ok("Shipment status for the shipment of tracking number " + trackingNo + " has been successfully updated"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking number " + trackingNo + " not found"));
    }

    @PatchMapping("/{trackingNo}/recipientAddress")
    @Transactional
    public ResponseEntity<String> updateShipmentInfoRecipientAddress(@PathVariable UUID trackingNo,
                                                                  @RequestBody AddressDTO addressDTO) {
        Optional<String> updateResult = updateShipmentRecipientAddressByTrackingNoCommand.execute(trackingNo, addressDTO);

        return updateResult
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking number " + trackingNo + "not found"));
    }

    @DeleteMapping("/{trackingNo}")
    public ResponseEntity<String> deleteShipmentInfoByTrackingNo(@PathVariable UUID trackingNo) {
        Optional<String> deleteResult = deleteShipmentByTrackingNoCommand.execute(trackingNo);
        return deleteResult
                .map(result -> ResponseEntity.ok("Shipment for the shipment of tracking number " + trackingNo + " has been successfully deleted"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment with tracking number " + trackingNo + " not found"));
    }
}
