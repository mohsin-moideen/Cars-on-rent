package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.CarPrice;
import org.carsonrent.rentals.service.dto.CarPriceDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class CarPriceMapperImpl implements CarPriceMapper {

    @Override
    public CarPrice toEntity(CarPriceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CarPrice carPrice = new CarPrice();

        carPrice.setId( dto.getId() );
        carPrice.setPricePerHour( dto.getPricePerHour() );
        carPrice.setDepositAmount( dto.getDepositAmount() );

        return carPrice;
    }

    @Override
    public CarPriceDTO toDto(CarPrice entity) {
        if ( entity == null ) {
            return null;
        }

        CarPriceDTO carPriceDTO = new CarPriceDTO();

        carPriceDTO.setId( entity.getId() );
        carPriceDTO.setPricePerHour( entity.getPricePerHour() );
        carPriceDTO.setDepositAmount( entity.getDepositAmount() );

        return carPriceDTO;
    }

    @Override
    public List<CarPrice> toEntity(List<CarPriceDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CarPrice> list = new ArrayList<CarPrice>();
        for ( CarPriceDTO carPriceDTO : dtoList ) {
            list.add( toEntity( carPriceDTO ) );
        }

        return list;
    }

    @Override
    public List<CarPriceDTO> toDto(List<CarPrice> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CarPriceDTO> list = new ArrayList<CarPriceDTO>();
        for ( CarPrice carPrice : entityList ) {
            list.add( toDto( carPrice ) );
        }

        return list;
    }
}
