package com.azshiptest.azshipapp.dto;

public record Address(
        String streetName,
        String neighbourhood,
        String city,
        StateCodeEnum stateCodeEnum,
        int addressNumber,
        String zipCode
) {
}
