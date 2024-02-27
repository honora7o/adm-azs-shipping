package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateShipmentStatusByTrackingNoCommandTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private UpdateShipmentStatusByTrackingNoCommand command;

    public UpdateShipmentStatusByTrackingNoCommandTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecute_Success() {
        UUID trackingNo = UUID.randomUUID();
        ShipmentStatusEnum newStatus = ShipmentStatusEnum.DELIVERED;

        when(shipmentRepository.updateShipmentStatusByTrackingNo(trackingNo, newStatus)).thenReturn(1);

        Optional<String> result = command.execute(trackingNo, newStatus);

        assertEquals(Optional.of("Shipment status successfully updated."), result);
        verify(shipmentRepository, times(1)).updateShipmentStatusByTrackingNo(trackingNo, newStatus);
    }

    @Test
    void testExecute_Failure() {
        UUID trackingNo = UUID.randomUUID();
        ShipmentStatusEnum newStatus = ShipmentStatusEnum.DELIVERED;

        when(shipmentRepository.updateShipmentStatusByTrackingNo(trackingNo, newStatus)).thenReturn(0);

        Optional<String> result = command.execute(trackingNo, newStatus);

        assertEquals(Optional.empty(), result);
        verify(shipmentRepository, times(1)).updateShipmentStatusByTrackingNo(trackingNo, newStatus);
    }
}
