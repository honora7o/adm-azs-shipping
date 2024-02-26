package com.azshiptest.azshipapp.infra.entities;

import com.azshiptest.azshipapp.models.StateCodeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "addresses_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String streetName;

    @NotNull
    private String neighbourhood;

    @NotNull
    private String city;

    @Enumerated(EnumType.STRING)
    @NotNull

    private StateCodeEnum stateCodeEnum;

    @NotNull
    private int addressNumber;

    @NotNull
    @Pattern(regexp = "\\d{8}", message = "Enter a valid zipcode.")
    private String zipCode;
}
