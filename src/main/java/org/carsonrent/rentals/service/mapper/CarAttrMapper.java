package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.CarAttrDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarAttr and its DTO CarAttrDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, })
public interface CarAttrMapper extends EntityMapper <CarAttrDTO, CarAttr> {

    @Mapping(source = "car.id", target = "carId")
    CarAttrDTO toDto(CarAttr carAttr); 

    @Mapping(source = "carId", target = "car")
    CarAttr toEntity(CarAttrDTO carAttrDTO); 
    default CarAttr fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarAttr carAttr = new CarAttr();
        carAttr.setId(id);
        return carAttr;
    }
}
