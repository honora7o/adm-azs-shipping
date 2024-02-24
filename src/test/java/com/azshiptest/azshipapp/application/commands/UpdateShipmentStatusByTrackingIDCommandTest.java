package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UpdateShipmentStatusByTrackingIDCommandTest {

    private ShipmentInfoRepository mockRepository;
    private UpdateShipmentStatusByTrackingIDCommand command;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ShipmentInfoRepository.class);
        command = new UpdateShipmentStatusByTrackingIDCommand(mockRepository);
    }

    @Test
    void execute() {
        String trackingID = "MG123456789SP";
        ShipmentStatusEnum newStatus = ShipmentStatusEnum.DELIVERED;

        Mockito.when(mockRepository.updateShipmentStatusByTrackingID(trackingID, newStatus))
                .thenReturn(1);

        int updatedRows = command.execute(trackingID, newStatus);

        verify(mockRepository, times(1)).updateShipmentStatusByTrackingID(trackingID, newStatus);

        assertEquals(1, updatedRows);
    }
}
