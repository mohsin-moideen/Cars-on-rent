package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.CarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car and its DTO CarDTO.
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class, CarPriceMapper.class, })
public interface CarMapper extends EntityMapper <CarDTO, Car> {

    @Mapping(source = "provider.id", target = "providerId")

    @Mapping(source = "carPrice.id", target = "carPriceId")
    CarDTO toDto(Car car); 

    @Mapping(source = "providerId", target = "provider")

    @Mapping(source = "carPriceId", target = "carPrice")
    @Mapping(target = "carAttrs", ignore = true)
    @Mapping(target = "availabilities", ignore = true)
    Car toEntity(CarDTO carDTO); 
    default Car fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }
}
