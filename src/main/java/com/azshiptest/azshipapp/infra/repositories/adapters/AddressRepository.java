package com.azshiptest.azshipapp.infra.repositories.adapters;

import com.azshiptest.azshipapp.infra.repositories.ports.AddressRepositoryPort;
import com.azshiptest.azshipapp.infra.entities.AddressEntity;
import com.azshiptest.azshipapp.models.StateCodeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>, AddressRepositoryPort {

    @Override
    @Modifying
    @Query("UPDATE Address a SET a.streetName = :streetName, a.neighbourhood = :neighbourhood, " +
            "a.city = :city, a.stateCodeEnum = :stateCodeEnum, " +
            "a.addressNumber = :addressNumber, a.zipCode = :zipCode WHERE a.id = :addressId")
    int updateAddressById(@Param("addressId") Long addressId, @Param("streetName") String streetName,
                          @Param("neighbourhood") String neighbourhood, @Param("city") String city,
                          @Param("stateCodeEnum") StateCodeEnum stateCodeEnum, @Param("addressNumber") int addressNumber,
                          @Param("zipCode") String zipCode);

}
