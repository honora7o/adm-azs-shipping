package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FindShipmentByTrackingIDQueryTest {

    @Mock
    private ShipmentInfoRepository mockRepository;
    private FindShipmentByTrackingIDQuery query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        query = new FindShipmentByTrackingIDQuery(mockRepository);
    }

    @Test
    void execute_ShouldReturnShipmentInfo_WhenTrackingIDExists() {
        String trackingID = "MG123456789SP";
        ShipmentEntity expectedShipmentEntity = new ShipmentEntity();
        when(mockRepository.findByTrackingID(trackingID)).thenReturn(Optional.of(expectedShipmentEntity));

        Optional<ShipmentEntity> result = query.execute(trackingID);

        assertEquals(expectedShipmentEntity, result.orElse(null));
    }

    @Test
    void execute_ShouldReturnEmptyOptional_WhenTrackingIDDoesNotExist() {
        String trackingID = "INVALIDTRACKINGID";
        when(mockRepository.findByTrackingID(trackingID)).thenReturn(Optional.empty());

        Optional<ShipmentEntity> result = query.execute(trackingID);

        assertEquals(Optional.empty(), result);
    }
}
