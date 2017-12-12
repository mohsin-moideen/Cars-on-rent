package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Coordinates;
import org.carsonrent.rentals.service.dto.CoordinatesDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class CoordinatesMapperImpl implements CoordinatesMapper {

    @Override
    public Coordinates toEntity(CoordinatesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Coordinates coordinates = new Coordinates();

        coordinates.setId( dto.getId() );
        coordinates.setLognitude( dto.getLognitude() );
        coordinates.setLatitude( dto.getLatitude() );

        return coordinates;
    }

    @Override
    public CoordinatesDTO toDto(Coordinates entity) {
        if ( entity == null ) {
            return null;
        }

        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();

        coordinatesDTO.setId( entity.getId() );
        coordinatesDTO.setLognitude( entity.getLognitude() );
        coordinatesDTO.setLatitude( entity.getLatitude() );

        return coordinatesDTO;
    }

    @Override
    public List<Coordinates> toEntity(List<CoordinatesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Coordinates> list = new ArrayList<Coordinates>();
        for ( CoordinatesDTO coordinatesDTO : dtoList ) {
            list.add( toEntity( coordinatesDTO ) );
        }

        return list;
    }

    @Override
    public List<CoordinatesDTO> toDto(List<Coordinates> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CoordinatesDTO> list = new ArrayList<CoordinatesDTO>();
        for ( Coordinates coordinates : entityList ) {
            list.add( toDto( coordinates ) );
        }

        return list;
    }
}
