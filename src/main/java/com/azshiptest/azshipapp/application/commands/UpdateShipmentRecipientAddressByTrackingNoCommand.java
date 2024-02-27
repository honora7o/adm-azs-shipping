package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.dto.AddressDTO;
import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateShipmentRecipientAddressByTrackingNoCommand {
    private final ShipmentRepository shipmentRepository;
    private final AddressRepository addressRepository;

    public UpdateShipmentRecipientAddressByTrackingNoCommand(ShipmentRepository shipmentRepository, AddressRepository addressRepository) {
        this.shipmentRepository = shipmentRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Optional<String> execute(UUID trackingNo, AddressDTO newAddress) {
        return shipmentRepository.findByTrackingNo(trackingNo)
                .map(shipment -> {
                    updateAddress(shipment.getRecipientAddress().getId(), newAddress);
                    return Optional.of("Address updated successfully.");
                })
                .orElse(Optional.empty());
    }

    private void updateAddress(UUID addressId, AddressDTO newAddress) {
        addressRepository.updateAddressById(
                addressId,
                newAddress.streetName(),
                newAddress.neighbourhood(),
                newAddress.city(),
                newAddress.stateCodeEnum(),
                newAddress.addressNumber(),
                newAddress.zipCode()
        );
    }
}

