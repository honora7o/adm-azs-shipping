package com.azshiptest.azshipapp.domain;

public record Address(
        String streetName,
        String neighbourhood,
        String city,
        StateCodeEnum stateCodeEnum,
        int addressNumber,
        String zipCode
) {
}
