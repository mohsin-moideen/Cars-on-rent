package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.ProviderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Provider and its DTO ProviderDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, })
public interface ProviderMapper extends EntityMapper <ProviderDTO, Provider> {

    @Mapping(source = "location.id", target = "locationId")
    ProviderDTO toDto(Provider provider); 

    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "cars", ignore = true)
    Provider toEntity(ProviderDTO providerDTO); 
    default Provider fromId(Long id) {
        if (id == null) {
            return null;
        }
        Provider provider = new Provider();
        provider.setId(id);
        return provider;
    }
}
