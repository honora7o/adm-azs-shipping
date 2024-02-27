package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.dto.AddressDTO;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateShipmentRecipientAddressByTrackingNoCommandTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UpdateShipmentRecipientAddressByTrackingNoCommand command;

    public UpdateShipmentRecipientAddressByTrackingNoCommandTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecute_Success() {
        UUID trackingNo = UUID.randomUUID();
        AddressDTO newAddress = new AddressDTO(
                "Rua Nova",
                "Bairro novo",
                "Cidade nova",
                StateCodeEnum.MG,
                "123",
                "12345678"
        );

        ShipmentEntity shipmentEntity = new ShipmentEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(UUID.randomUUID());
        shipmentEntity.setRecipientAddress(addressEntity);

        when(shipmentRepository.findByTrackingNo(trackingNo)).thenReturn(Optional.of(shipmentEntity));
        when(addressRepository.updateAddressById(any(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        Optional<String> result = command.execute(trackingNo, newAddress);

        assertEquals(Optional.of("Address updated successfully."), result);
        verify(shipmentRepository, times(1)).findByTrackingNo(trackingNo);
        verify(addressRepository, times(1)).updateAddressById(
                eq(addressEntity.getId()),
                eq(newAddress.streetName()),
                eq(newAddress.neighbourhood()),
                eq(newAddress.city()),
                eq(newAddress.stateCodeEnum()),
                eq(newAddress.addressNumber()),
                eq(newAddress.zipCode())
        );
    }

    @Test
    void testExecute_ShipmentNotFound() {
        UUID trackingNo = UUID.randomUUID();
        AddressDTO newAddress = new AddressDTO(
                "Rua Nova",
                "Bairro novo",
                "Cidade nova",
                StateCodeEnum.MG,
                "123",
                "12345678"
        );

        when(shipmentRepository.findByTrackingNo(trackingNo)).thenReturn(Optional.empty());

        Optional<String> result = command.execute(trackingNo, newAddress);

        assertEquals(Optional.empty(), result);
        verify(shipmentRepository, times(1)).findByTrackingNo(trackingNo);
        verify(addressRepository, never()).updateAddressById(any(), any(), any(), any(), any(), any(), any());
    }
}
