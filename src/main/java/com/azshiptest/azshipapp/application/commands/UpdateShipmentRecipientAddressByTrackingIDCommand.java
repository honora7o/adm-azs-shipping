package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateShipmentRecipientAddressByTrackingIDCommand {
    private final ShipmentInfoRepository shipmentInfoRepository;
    private final AddressRepository addressRepository;

    public UpdateShipmentRecipientAddressByTrackingIDCommand(ShipmentInfoRepository shipmentInfoRepository, AddressRepository addressRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public int execute(String trackingID, AddressEntity newRecipientAddressEntity) {
        Optional<ShipmentEntity> optionalShipmentInfo = shipmentInfoRepository.findByTrackingID(trackingID);

        if (optionalShipmentInfo.isEmpty()) {
            return 0;
        }

        ShipmentEntity shipmentEntity = optionalShipmentInfo.get();
        Long addressId = shipmentEntity.getRecipientAddressEntity().getId();

        addressRepository.updateAddressById(addressId, newRecipientAddressEntity.getStreetName(),
                newRecipientAddressEntity.getNeighbourhood(), newRecipientAddressEntity.getCity(),
                newRecipientAddressEntity.getStateCodeEnum(), newRecipientAddressEntity.getAddressNumber(),
                newRecipientAddressEntity.getZipCode());

        return 1;
    }
}

