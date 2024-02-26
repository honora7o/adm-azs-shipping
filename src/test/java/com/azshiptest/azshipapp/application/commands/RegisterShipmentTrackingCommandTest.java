package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.infra.entities.ShipmentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RegisterShipmentTrackingCommandTest {

    private ShipmentInfoRepository mockRepository;
    private RegisterShipmentTrackingCommand command;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ShipmentInfoRepository.class);
        command = new RegisterShipmentTrackingCommand(mockRepository);
    }

    @Test
    void registerShipmentTracking() {
        AddressEntity senderAddressEntity = AddressEntity.builder()
                .streetName("Afranio Rodrigues")
                .neighbourhood("Fundinho")
                .city("Uberlandia")
                .stateCodeEnum(StateCodeEnum.MG)
                .addressNumber(198)
                .zipCode("38000")
                .build();

        AddressEntity recipientAddressEntity = AddressEntity.builder()
                .streetName("Av Paulista")
                .neighbourhood("Bela Vista")
                .city("São Paulo")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(810)
                .zipCode("01310-100")
                .build();

        ShipmentInfoFormInput shipmentInfoFormInput = new ShipmentInfoFormInput(
                "12345678922",
                senderAddressEntity,
                recipientAddressEntity,
                BigDecimal.valueOf(100.00),
                Optional.of(5),
                Optional.of(3.5f)
        );

        ShipmentEntity savedShipmentEntity = command.execute(shipmentInfoFormInput);

        verify(mockRepository, times(1)).save(savedShipmentEntity);
        verify(mockRepository).save(argThat(argument -> {
            assertEquals("12345678922", argument.getTaxPayerRegistrationNo());
            assertEquals(senderAddressEntity, argument.getSenderAddressEntity());
            assertEquals(recipientAddressEntity, argument.getRecipientAddressEntity());
            assertTrue(argument.getTrackingID().matches("[A-Z]{2}\\d{9}[A-Z]{2}")); // not good, only checks if there are 9 rng numbers between 2 capitalized pair of letters
            assertEquals(LocalDate.now(), argument.getPostingDate());
            assertEquals(ShipmentStatusEnum.POSTED, argument.getShipmentStatus());
            return true;
        }));
    }
}
