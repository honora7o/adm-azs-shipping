package com.azshiptest.azshipapp.application.commands;

import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import com.azshiptest.azshipapp.models.Address;
import com.azshiptest.azshipapp.infra.repositories.adapters.ShipmentInfoRepository;
import com.azshiptest.azshipapp.dto.ShipmentInfoFormInput;
import com.azshiptest.azshipapp.models.ShipmentInfo;
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
    void execute() {
        Address senderAddress = Address.builder()
                .streetName("Afranio Rodrigues")
                .neighbourhood("Fundinho")
                .city("Uberlandia")
                .stateCodeEnum(StateCodeEnum.MG)
                .addressNumber(198)
                .zipCode("38000")
                .build();

        Address recipientAddress = Address.builder()
                .streetName("Av Paulista")
                .neighbourhood("Bela Vista")
                .city("São Paulo")
                .stateCodeEnum(StateCodeEnum.SP)
                .addressNumber(810)
                .zipCode("01310-100")
                .build();

        ShipmentInfoFormInput shipmentInfoFormInput = new ShipmentInfoFormInput(
                "12345678922",
                senderAddress,
                recipientAddress,
                BigDecimal.valueOf(100.00),
                Optional.of(5),
                Optional.of(3.5f)
        );

        ShipmentInfo savedShipmentInfo = command.execute(shipmentInfoFormInput);

        verify(mockRepository, times(1)).save(savedShipmentInfo);
        verify(mockRepository).save(argThat(argument -> {
            assertEquals(senderAddress, argument.getSenderAddress());
            assertEquals(recipientAddress, argument.getRecipientAddress());
            assertTrue(argument.getTrackingID().matches("[A-Z]{2}\\d{9}[A-Z]{2}")); // not good, only checks if there are 9 rng numbers between 2 capitalized pair of letters
            assertEquals(LocalDate.now(), argument.getPostingDate());
            assertEquals(ShipmentStatusEnum.POSTED, argument.getShipmentStatus());
            return true;
        }));
    }
}
