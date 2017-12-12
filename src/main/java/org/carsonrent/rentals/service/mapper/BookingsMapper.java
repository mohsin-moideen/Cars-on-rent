package org.carsonrent.rentals.service.mapper;

import org.carsonrent.rentals.domain.*;
import org.carsonrent.rentals.service.dto.BookingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bookings and its DTO BookingsDTO.
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class, UserMapper.class, })
public interface BookingsMapper extends EntityMapper <BookingsDTO, Bookings> {

    @Mapping(source = "provider.id", target = "providerId")

    @Mapping(source = "user.id", target = "userId")
    BookingsDTO toDto(Bookings bookings); 

    @Mapping(source = "providerId", target = "provider")

    @Mapping(source = "userId", target = "user")
    Bookings toEntity(BookingsDTO bookingsDTO); 
    default Bookings fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bookings bookings = new Bookings();
        bookings.setId(id);
        return bookings;
    }
}
