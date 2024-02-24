package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateShipmentRecipientAddressByTrackingIDCommandTest {

    @Mock
    private ShipmentInfoRepository mockShipmentInfoRepository;
    @Mock
    private AddressRepository mockAddressRepository;

    private UpdateShipmentRecipientAddressByTrackingIDCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        command = new UpdateShipmentRecipientAddressByTrackingIDCommand(mockShipmentInfoRepository, mockAddressRepository);
    }

    @Test
    void execute_ShouldReturnZero_WhenShipmentInfoDoesNotExist() {
        // Arrange
        String trackingID = "ABC123456DEF";
        when(mockShipmentInfoRepository.findByTrackingID(trackingID)).thenReturn(Optional.empty());

        // Act
        int result = command.execute(trackingID, new Address());

        // Assert
        assertEquals(0, result);
    }

    @Test
    void execute_ShouldUpdateRecipientAddressAndReturnOne_WhenShipmentInfoExists() {
        // Arrange
        String trackingID = "ABC123456DEF";
        Address newRecipientAddress = new Address();
        ShipmentInfo shipmentInfo = new ShipmentInfo();
        shipmentInfo.setRecipientAddress(new Address());
        Optional<ShipmentInfo> optionalShipmentInfo = Optional.of(shipmentInfo);
        when(mockShipmentInfoRepository.findByTrackingID(trackingID)).thenReturn(optionalShipmentInfo);

        // Act
        int result = command.execute(trackingID, newRecipientAddress);

        // Assert
        assertEquals(1, result);
        verify(mockShipmentInfoRepository).findByTrackingID(trackingID);
        verify(mockAddressRepository).updateAddressById(any(), any(), any(), any(), any(), any(), any());
    }
}
