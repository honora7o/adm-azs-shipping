package com.azshiptest.azshipapp.dto;

import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
        @NotNull
        String streetName,

        @NotNull
        String neighbourhood,

        @NotNull
        String city,

        @NotNull StateCodeEnum stateCodeEnum,

        @NotNull
        String addressNumber,

        @NotNull @Pattern (regexp = "\\d{8}", message = "Enter a valid zipcode.")
        String zipCode
) {
    public static AddressDTO fromEntity(AddressEntity addressEntity) {
        return new AddressDTO(
                addressEntity.getStreetName(),
                addressEntity.getNeighbourhood(),
                addressEntity.getCity(),
                addressEntity.getStateCodeEnum(),
                addressEntity.getAddressNumber(),
                addressEntity.getZipCode()
        );
    }
}
