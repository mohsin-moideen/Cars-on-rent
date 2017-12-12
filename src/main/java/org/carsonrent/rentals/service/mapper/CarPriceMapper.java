package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.CarPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarPrice and its DTO CarPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarPriceMapper extends EntityMapper <CarPriceDTO, CarPrice> {
    
    
    default CarPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarPrice carPrice = new CarPrice();
        carPrice.setId(id);
        return carPrice;
    }
}
