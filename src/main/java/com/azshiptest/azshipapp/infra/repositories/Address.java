package com.azshiptest.azshipapp.dto;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Address(
        @Id
        Long id,
        String streetName,
        String neighbourhood,
        String city,
        StateCodeEnum stateCodeEnum,
        int addressNumber,
        String zipCode
) {
}
