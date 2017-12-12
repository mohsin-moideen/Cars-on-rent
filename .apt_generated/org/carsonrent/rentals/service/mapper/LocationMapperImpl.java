package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Coordinates;
import org.carsonrent.rentals.domain.Location;
import org.carsonrent.rentals.service.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:54+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Autowired
    private CoordinatesMapper coordinatesMapper;

    @Override
    public List<Location> toEntity(List<LocationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Location> list = new ArrayList<Location>();
        for ( LocationDTO locationDTO : dtoList ) {
            list.add( toEntity( locationDTO ) );
        }

        return list;
    }

    @Override
    public List<LocationDTO> toDto(List<Location> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LocationDTO> list = new ArrayList<LocationDTO>();
        for ( Location location : entityList ) {
            list.add( toDto( location ) );
        }

        return list;
    }

    @Override
    public LocationDTO toDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDTO locationDTO_ = new LocationDTO();

        locationDTO_.setCoordinatesId( locationCoordinatesId( location ) );
        locationDTO_.setId( location.getId() );
        locationDTO_.setStreetAddress( location.getStreetAddress() );
        locationDTO_.setPostalCode( location.getPostalCode() );
        locationDTO_.setCity( location.getCity() );
        locationDTO_.setStateProvince( location.getStateProvince() );
        locationDTO_.setCountryName( location.getCountryName() );

        return locationDTO_;
    }

    @Override
    public Location toEntity(LocationDTO locationDTO) {
        if ( locationDTO == null ) {
            return null;
        }

        Location location_ = new Location();

        location_.setCoordinates( coordinatesMapper.fromId( locationDTO.getCoordinatesId() ) );
        location_.setId( locationDTO.getId() );
        location_.setStreetAddress( locationDTO.getStreetAddress() );
        location_.setPostalCode( locationDTO.getPostalCode() );
        location_.setCity( locationDTO.getCity() );
        location_.setStateProvince( locationDTO.getStateProvince() );
        location_.setCountryName( locationDTO.getCountryName() );

        return location_;
    }

    private Long locationCoordinatesId(Location location) {

        if ( location == null ) {
            return null;
        }
        Coordinates coordinates = location.getCoordinates();
        if ( coordinates == null ) {
            return null;
        }
        Long id = coordinates.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
