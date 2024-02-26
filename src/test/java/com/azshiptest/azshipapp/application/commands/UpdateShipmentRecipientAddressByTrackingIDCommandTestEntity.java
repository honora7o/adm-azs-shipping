package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateShipmentRecipientAddressByTrackingIDCommandTestEntity {

    private ShipmentInfoRepository mockShipmentInfoRepository;
    private AddressRepository mockAddressRepository;
    private UpdateShipmentRecipientAddressByTrackingIDCommand command;

    @BeforeEach
    void setUp() {
        mockShipmentInfoRepository = Mockito.mock(ShipmentInfoRepository.class);
        mockAddressRepository = Mockito.mock(AddressRepository.class);
        command = new UpdateShipmentRecipientAddressByTrackingIDCommand(mockShipmentInfoRepository, mockAddressRepository);
    }

    @Test
    void shouldUpdateRecipientAddress_WhenValidTrackingID() {
        String trackingID = "MG123456789SP";
        AddressEntity newRecipientAddressEntity = AddressEntity.builder()
                .streetName("Av Teste")
                .neighbourhood("Bairro Teste 1")
                .city("Sao Paulo")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(123)
                .zipCode("12345")
                .build();

        ShipmentEntity shipmentEntity = new ShipmentEntity();
        AddressEntity oldRecipientAddressEntity = AddressEntity.builder()
                .streetName("Rua Teste")
                .neighbourhood("Bairro Teste 2")
                .city("Pindamonhangaba")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(456)
                .zipCode("67890")
                .build();
        shipmentEntity.setRecipientAddressEntity(oldRecipientAddressEntity);

        when(mockShipmentInfoRepository.findByTrackingID(trackingID)).thenReturn(Optional.of(shipmentEntity));

        int result = command.execute(trackingID, newRecipientAddressEntity);

        verify(mockShipmentInfoRepository, times(1)).findByTrackingID(trackingID);
        verify(mockAddressRepository, times(1)).updateAddressById(eq(shipmentEntity.getRecipientAddressEntity().getId()), eq("Av Teste"), eq("Bairro Teste 1"), eq("Sao Paulo"), eq(StateCodeEnum.SP), eq(123), eq("12345"));
        assertEquals(1, result);
    }

    @Test
    void shouldReturnZero_WhenInvalidTrackingID() {
        String trackingID = "INVALIDTRACKINGID";
        AddressEntity newRecipientAddressEntity = AddressEntity.builder()
                .streetName("Av Teste")
                .neighbourhood("Bairro Teste 1")
                .city("Sao Paulo")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(123)
                .zipCode("12345")
                .build();

        when(mockShipmentInfoRepository.findByTrackingID(trackingID)).thenReturn(Optional.empty());

        int result = command.execute(trackingID, newRecipientAddressEntity);

        verify(mockShipmentInfoRepository, times(1)).findByTrackingID(trackingID);
        verify(mockAddressRepository, times(0)).updateAddressById(anyLong(), anyString(), anyString(), anyString(), any(), anyInt(), anyString());
        assertEquals(0, result);
    }
}
