package com.azshiptest.azshipapp.infra.entities;

import com.azshiptest.azshipapp.dto.AddressDTO;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "addresses_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String streetName;
    private String neighbourhood;
    private String city;

    @Enumerated(EnumType.STRING)
    private StateCodeEnum stateCodeEnum;

    private String addressNumber;
    private String zipCode;

    public static AddressEntity fromDTO(AddressDTO addressDTO) {
        return AddressEntity.builder()
                .streetName(addressDTO.streetName())
                .neighbourhood(addressDTO.neighbourhood())
                .city(addressDTO.city())
                .stateCodeEnum(addressDTO.stateCodeEnum())
                .addressNumber(addressDTO.addressNumber())
                .zipCode(addressDTO.zipCode())
                .build();
    }
}
