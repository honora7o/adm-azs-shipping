package com.azshiptest.azshipapp.infra.repositories;

import com.azshiptest.azshipapp.dto.StateCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String streetName;
    private String neighbourhood;
    private String city;

    @Enumerated(EnumType.STRING)
    private StateCodeEnum stateCodeEnum;

    private int addressNumber;
    private String zipCode;
}
