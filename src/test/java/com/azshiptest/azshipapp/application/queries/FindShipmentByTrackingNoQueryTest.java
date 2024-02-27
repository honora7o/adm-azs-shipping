package com.azshiptest.azshipapp.application.queries;

import com.azshiptest.azshipapp.dto.AddressDTO;
import com.azshiptest.azshipapp.dto.ShipmentDTO;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentRepository;
import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FindShipmentByTrackingNoQueryTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private FindShipmentByTrackingNoQuery query;

    public FindShipmentByTrackingNoQueryTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnShipmentDTO_WhenShipmentFound() {
        UUID trackingNo = UUID.randomUUID();
        ShipmentEntity shipmentEntity = createMockShipmentEntity(trackingNo);

        when(shipmentRepository.findByTrackingNo(trackingNo)).thenReturn(Optional.of(shipmentEntity));

        Optional<ShipmentDTO> result = query.execute(trackingNo);

        assertEquals(createExpectedShipmentDTO(trackingNo), result);
        verify(shipmentRepository, times(1)).findByTrackingNo(trackingNo);
    }

    @Test
    void shouldReturnEmptyOptional_WhenShipmentNotFound() {
        UUID trackingNo = UUID.randomUUID();

        when(shipmentRepository.findByTrackingNo(trackingNo)).thenReturn(Optional.empty());

        Optional<ShipmentDTO> result = query.execute(trackingNo);

        assertEquals(Optional.empty(), result);
        verify(shipmentRepository, times(1)).findByTrackingNo(trackingNo);
    }

    private ShipmentEntity createMockShipmentEntity(UUID trackingNo) {
        return ShipmentEntity.builder()
                .trackingNo(trackingNo)
                .taxPayerRegistrationNo("1234567890")
                .senderAddress(createMockAddressEntity())
                .recipientAddress(createMockAddressEntity())
                .shipmentStatus(ShipmentStatusEnum.DELIVERED)
                .postingDate(LocalDate.of(2022, 1, 1))
                .estimatedArrivalDate(LocalDate.of(2022, 1, 5))
                .value(BigDecimal.valueOf(100))
                .weight(10)
                .cubingMeasurement(50.0f)
                .build();
    }

    private AddressEntity createMockAddressEntity() {
        return AddressEntity.builder()
                .id(UUID.randomUUID())
                .streetName("Rua real")
                .neighbourhood("Bairro oficial")
                .city("Cidade verificada")
                .stateCodeEnum(StateCodeEnum.MG)
                .addressNumber("123")
                .zipCode("12345678")
                .build();
    }

    private Optional<ShipmentDTO> createExpectedShipmentDTO(UUID trackingNo) {
        AddressDTO senderAddressDTO = new AddressDTO(
                "Rua real",
                "Bairro oficial",
                "Cidade verificada",
                StateCodeEnum.MG,
                "123",
                "12345678"
        );
        AddressDTO recipientAddressDTO = new AddressDTO(
                "Rua real",
                "Bairro oficial",
                "Cidade verificada",
                StateCodeEnum.MG,
                "123",
                "12345678"
        );
        return Optional.of(new ShipmentDTO(
                trackingNo,
                "1234567890",
                senderAddressDTO,
                recipientAddressDTO,
                ShipmentStatusEnum.DELIVERED,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 5),
                BigDecimal.valueOf(100),
                10,
                50.0f
        ));
    }

}
