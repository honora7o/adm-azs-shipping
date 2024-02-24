package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteShipmentByTrackingIDCommandTest {

    @Mock
    private ShipmentInfoRepository mockRepository;
    private DeleteShipmentByTrackingIDCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        command = new DeleteShipmentByTrackingIDCommand(mockRepository);
    }

    @Test
    void execute_ShouldReturnNumberOfDeletedEntries_WhenTrackingIDExists() {
        String trackingID = "MG123456789SP";
        int expectedDeletedEntries = 1;
        when(mockRepository.deleteByTrackingID(trackingID)).thenReturn(expectedDeletedEntries);

        int actualDeletedEntries = command.execute(trackingID);

        assertEquals(expectedDeletedEntries, actualDeletedEntries);
        verify(mockRepository).deleteByTrackingID(trackingID);
    }

    @Test
    void execute_ShouldReturnZero_WhenTrackingIDDoesNotExist() {
        String trackingID = "INVALIDTRACKINGID";
        int expectedDeletedEntries = 0;
        when(mockRepository.deleteByTrackingID(trackingID)).thenReturn(expectedDeletedEntries);

        int actualDeletedEntries = command.execute(trackingID);

        assertEquals(expectedDeletedEntries, actualDeletedEntries);
        verify(mockRepository).deleteByTrackingID(trackingID);
    }
}
