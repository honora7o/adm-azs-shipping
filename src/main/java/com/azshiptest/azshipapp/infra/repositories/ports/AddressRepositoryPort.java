package com.azshiptest.azshipapp.infra.repositories.ports;

import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AddressRepositoryPort {
    int updateAddressById(@Param("addressId") UUID addressId, @Param("streetName") String streetName,
                          @Param("neighbourhood") String neighbourhood, @Param("city") String city,
                          @Param("stateCodeEnum") StateCodeEnum stateCodeEnum, @Param("addressNumber") String addressNumber,
                          @Param("zipCode") String zipCode);
}
