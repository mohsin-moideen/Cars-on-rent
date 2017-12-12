package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.CoordinatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coordinates and its DTO CoordinatesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoordinatesMapper extends EntityMapper <CoordinatesDTO, Coordinates> {
    
    
    default Coordinates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coordinates coordinates = new Coordinates();
        coordinates.setId(id);
        return coordinates;
    }
}
