package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.dto.ChangeAddressRequest;
import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
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
    public int execute(UUID trackingNo, ChangeAddressRequest newAddress) {
        Optional<ShipmentEntity> optionalShipmentInfo = shipmentRepository.findByTrackingNo(trackingNo);

        if (optionalShipmentInfo.isEmpty()) {
            return 0;
        }

        ShipmentEntity shipmentEntity = optionalShipmentInfo.get();
        UUID addressId = shipmentEntity.getRecipientAddress().getId();

        addressRepository.updateAddressById(addressId, newAddress.streetName(),
                newAddress.neighbourhood(), newAddress.city(),
                newAddress.stateCodeEnum(), newAddress.addressNumber(),
                newAddress.zipCode());

        return 1;
    }
}

