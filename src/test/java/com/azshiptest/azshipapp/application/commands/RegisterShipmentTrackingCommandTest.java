package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.dto.AddressDTO;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RegisterShipmentTrackingCommandTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    private RegisterShipmentTrackingCommand shipmentTrackingCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shipmentTrackingCommand = new RegisterShipmentTrackingCommand(shipmentRepository);
    }

    @Test
    void shouldCallSaveMethodOnRepository() {
        AddressEntity senderAddressEntity = new AddressEntity();
        AddressEntity recipientAddressEntity = new AddressEntity();
        ShipmentInfoFormInput shipmentInfoFormInput = new ShipmentInfoFormInput(
                "12345678901",
                AddressDTO.fromEntity(senderAddressEntity),
                AddressDTO.fromEntity(recipientAddressEntity),
                BigDecimal.TEN,
                Optional.of(50),
                Optional.of(2.5f)
        );

        shipmentTrackingCommand.execute(shipmentInfoFormInput);

        verify(shipmentRepository, times(1)).save(any());
    }
}
