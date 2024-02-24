package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.infra.repositories.adapters.AddressRepository;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.models.ShipmentInfo;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    void execute_ShouldUpdateRecipientAddressAndReturnOne_WhenShipmentInfoExists() {
        String trackingID = "MG123456789SP";
        Address newRecipientAddress = Address.builder()
                .streetName("Av Teste")
                .neighbourhood("Bairro Teste 1")
                .city("Sao Paulo")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(123)
                .zipCode("12345")
                .build();
        ShipmentInfo shipmentInfo = new ShipmentInfo();
        shipmentInfo.setTrackingID(trackingID);
        Address oldRecipientAddress = Address.builder()
                .streetName("Rua Teste")
                .neighbourhood("Bairro Teste 2")
                .city("Pindamonhangaba")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(456)
                .zipCode("67890")
                .build();
        shipmentInfo.setRecipientAddress(oldRecipientAddress);

        when(mockShipmentInfoRepository.findByTrackingID(trackingID)).thenReturn(Optional.of(shipmentInfo));

        int result = command.execute(trackingID, newRecipientAddress);

        assertEquals(1, result);

        ArgumentCaptor<Long> addressIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> streetNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> neighbourhoodCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> cityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<StateCodeEnum> stateCodeEnumCaptor = ArgumentCaptor.forClass(StateCodeEnum.class);
        ArgumentCaptor<Integer> addressNumberCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> zipCodeCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockAddressRepository).updateAddressById(
                addressIdCaptor.capture(),
                streetNameCaptor.capture(),
                neighbourhoodCaptor.capture(),
                cityCaptor.capture(),
                stateCodeEnumCaptor.capture(),
                addressNumberCaptor.capture(),
                zipCodeCaptor.capture()
        );

        assertEquals(oldRecipientAddress.getId(), addressIdCaptor.getValue());
        assertEquals(newRecipientAddress.getStreetName(), streetNameCaptor.getValue());
        assertEquals(newRecipientAddress.getNeighbourhood(), neighbourhoodCaptor.getValue());
        assertEquals(newRecipientAddress.getCity(), cityCaptor.getValue());
        assertEquals(newRecipientAddress.getStateCodeEnum(), stateCodeEnumCaptor.getValue());
        assertEquals(newRecipientAddress.getAddressNumber(), addressNumberCaptor.getValue());
        assertEquals(newRecipientAddress.getZipCode(), zipCodeCaptor.getValue());
    }
}
