package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.models.ShipmentInfo;
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
    public int execute(String trackingID, Address newRecipientAddress) {
        Optional<ShipmentInfo> optionalShipmentInfo = shipmentInfoRepository.findByTrackingID(trackingID);

        if (optionalShipmentInfo.isEmpty()) {
            return 0;
        }

        ShipmentInfo shipmentInfo = optionalShipmentInfo.get();
        Long addressId = shipmentInfo.getRecipientAddress().getId();

        addressRepository.updateAddressById(addressId, newRecipientAddress.getStreetName(),
                newRecipientAddress.getNeighbourhood(), newRecipientAddress.getCity(),
                newRecipientAddress.getStateCodeEnum(), newRecipientAddress.getAddressNumber(),
                newRecipientAddress.getZipCode());

        return 1;
    }
}

