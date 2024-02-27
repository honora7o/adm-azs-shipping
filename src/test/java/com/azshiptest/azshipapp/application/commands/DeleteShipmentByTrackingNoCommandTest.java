package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeleteShipmentByTrackingNoCommandTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private DeleteShipmentByTrackingNoCommand deleteShipmentByTrackingNoCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnSuccessMessage_WhenDeletionSuccessful() {
        UUID trackingNo = UUID.randomUUID();
        when(shipmentRepository.deleteByTrackingNo(trackingNo)).thenReturn(1);

        Optional<String> result = deleteShipmentByTrackingNoCommand.execute(trackingNo);

        assertEquals(Optional.of("Shipment successfully deleted."), result);
    }

    @Test
    void shouldReturnEmptyOptional_WhenDeletionUnsuccessful() {
        UUID trackingNo = UUID.randomUUID();
        when(shipmentRepository.deleteByTrackingNo(trackingNo)).thenReturn(0);

        Optional<String> result = deleteShipmentByTrackingNoCommand.execute(trackingNo);

        assertEquals(Optional.empty(), result);
    }
}
