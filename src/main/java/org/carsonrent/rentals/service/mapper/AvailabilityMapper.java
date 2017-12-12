package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.AvailabilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Availability and its DTO AvailabilityDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, })
public interface AvailabilityMapper extends EntityMapper <AvailabilityDTO, Availability> {

    @Mapping(source = "car.id", target = "carId")
    AvailabilityDTO toDto(Availability availability); 

    @Mapping(source = "carId", target = "car")
    Availability toEntity(AvailabilityDTO availabilityDTO); 
    default Availability fromId(Long id) {
        if (id == null) {
            return null;
        }
        Availability availability = new Availability();
        availability.setId(id);
        return availability;
    }
}
