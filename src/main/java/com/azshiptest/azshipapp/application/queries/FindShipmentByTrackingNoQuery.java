package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.dto.AddressDTO;
import com.azshiptest.azshipapp.dto.ShipmentDTO;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindShipmentByTrackingNoQuery {
    private final ShipmentRepository shipmentRepository;

    public FindShipmentByTrackingNoQuery(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Optional<ShipmentDTO> execute(UUID trackingNo) {
        Optional<ShipmentEntity> optionalShipmentEntity = shipmentRepository.findByTrackingNo(trackingNo);

        return optionalShipmentEntity.map(shipmentEntity -> {
            AddressDTO senderAddressDTO = AddressDTO.fromEntity(shipmentEntity.getSenderAddress());
            AddressDTO recipientAddressDTO = AddressDTO.fromEntity(shipmentEntity.getRecipientAddress());

            return new ShipmentDTO(
                    shipmentEntity.getTrackingNo(),
                    shipmentEntity.getTaxPayerRegistrationNo(),
                    senderAddressDTO,
                    recipientAddressDTO,
                    shipmentEntity.getShipmentStatus(),
                    shipmentEntity.getPostingDate(),
                    shipmentEntity.getEstimatedArrivalDate(),
                    shipmentEntity.getValue(),
                    shipmentEntity.getWeight(),
                    shipmentEntity.getCubingMeasurement()
            );
        });
    }
}
