package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Availability;
import org.carsonrent.rentals.domain.Car;
import org.carsonrent.rentals.service.dto.AvailabilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class AvailabilityMapperImpl implements AvailabilityMapper {

    @Autowired
    private CarMapper carMapper;

    @Override
    public List<Availability> toEntity(List<AvailabilityDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Availability> list = new ArrayList<Availability>();
        for ( AvailabilityDTO availabilityDTO : dtoList ) {
            list.add( toEntity( availabilityDTO ) );
        }

        return list;
    }

    @Override
    public List<AvailabilityDTO> toDto(List<Availability> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AvailabilityDTO> list = new ArrayList<AvailabilityDTO>();
        for ( Availability availability : entityList ) {
            list.add( toDto( availability ) );
        }

        return list;
    }

    @Override
    public AvailabilityDTO toDto(Availability availability) {
        if ( availability == null ) {
            return null;
        }

        AvailabilityDTO availabilityDTO_ = new AvailabilityDTO();

        availabilityDTO_.setCarId( availabilityCarId( availability ) );
        availabilityDTO_.setId( availability.getId() );
        availabilityDTO_.setStartDate( availability.getStartDate() );
        availabilityDTO_.setEndDate( availability.getEndDate() );

        return availabilityDTO_;
    }

    @Override
    public Availability toEntity(AvailabilityDTO availabilityDTO) {
        if ( availabilityDTO == null ) {
            return null;
        }

        Availability availability_ = new Availability();

        availability_.setCar( carMapper.fromId( availabilityDTO.getCarId() ) );
        availability_.setId( availabilityDTO.getId() );
        availability_.setStartDate( availabilityDTO.getStartDate() );
        availability_.setEndDate( availabilityDTO.getEndDate() );

        return availability_;
    }

    private Long availabilityCarId(Availability availability) {

        if ( availability == null ) {
            return null;
        }
        Car car = availability.getCar();
        if ( car == null ) {
            return null;
        }
        Long id = car.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
