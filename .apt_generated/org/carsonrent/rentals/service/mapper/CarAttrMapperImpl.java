package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Car;
import org.carsonrent.rentals.domain.CarAttr;
import org.carsonrent.rentals.service.dto.CarAttrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class CarAttrMapperImpl implements CarAttrMapper {

    @Autowired
    private CarMapper carMapper;

    @Override
    public List<CarAttr> toEntity(List<CarAttrDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CarAttr> list = new ArrayList<CarAttr>();
        for ( CarAttrDTO carAttrDTO : dtoList ) {
            list.add( toEntity( carAttrDTO ) );
        }

        return list;
    }

    @Override
    public List<CarAttrDTO> toDto(List<CarAttr> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CarAttrDTO> list = new ArrayList<CarAttrDTO>();
        for ( CarAttr carAttr : entityList ) {
            list.add( toDto( carAttr ) );
        }

        return list;
    }

    @Override
    public CarAttrDTO toDto(CarAttr carAttr) {
        if ( carAttr == null ) {
            return null;
        }

        CarAttrDTO carAttrDTO_ = new CarAttrDTO();

        carAttrDTO_.setCarId( carAttrCarId( carAttr ) );
        carAttrDTO_.setId( carAttr.getId() );
        carAttrDTO_.setAttrname( carAttr.getAttrname() );
        carAttrDTO_.setAttrval( carAttr.getAttrval() );

        return carAttrDTO_;
    }

    @Override
    public CarAttr toEntity(CarAttrDTO carAttrDTO) {
        if ( carAttrDTO == null ) {
            return null;
        }

        CarAttr carAttr_ = new CarAttr();

        carAttr_.setCar( carMapper.fromId( carAttrDTO.getCarId() ) );
        carAttr_.setId( carAttrDTO.getId() );
        carAttr_.setAttrname( carAttrDTO.getAttrname() );
        carAttr_.setAttrval( carAttrDTO.getAttrval() );

        return carAttr_;
    }

    private Long carAttrCarId(CarAttr carAttr) {

        if ( carAttr == null ) {
            return null;
        }
        Car car = carAttr.getCar();
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
