package com.azshiptest.azshipapp.infra.repositories.ports;

import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.springframework.data.repository.query.Param;

public interface AddressRepositoryPort {
    int updateAddressById(@Param("addressId") Long addressId, @Param("streetName") String streetName,
                          @Param("neighbourhood") String neighbourhood, @Param("city") String city,
                          @Param("stateCodeEnum") StateCodeEnum stateCodeEnum, @Param("addressNumber") int addressNumber,
                          @Param("zipCode") String zipCode);
}
