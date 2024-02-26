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

    private String streetName;
    private String neighbourhood;
    private String city;

    @Enumerated(EnumType.STRING)
    private StateCodeEnum stateCodeEnum;

    private String addressNumber;
    private String zipCode;
}
