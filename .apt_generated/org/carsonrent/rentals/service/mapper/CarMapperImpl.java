package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Car;
import org.carsonrent.rentals.domain.CarPrice;
import org.carsonrent.rentals.domain.Provider;
import org.carsonrent.rentals.service.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private CarPriceMapper carPriceMapper;

    @Override
    public List<Car> toEntity(List<CarDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Car> list = new ArrayList<Car>();
        for ( CarDTO carDTO : dtoList ) {
            list.add( toEntity( carDTO ) );
        }

        return list;
    }

    @Override
    public List<CarDTO> toDto(List<Car> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CarDTO> list = new ArrayList<CarDTO>();
        for ( Car car : entityList ) {
            list.add( toDto( car ) );
        }

        return list;
    }

    @Override
    public CarDTO toDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO carDTO_ = new CarDTO();

        carDTO_.setProviderId( carProviderId( car ) );
        carDTO_.setCarPriceId( carCarPriceId( car ) );
        carDTO_.setId( car.getId() );
        carDTO_.setCapacity( car.getCapacity() );

        return carDTO_;
    }

    @Override
    public Car toEntity(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        Car car_ = new Car();

        car_.setCarPrice( carPriceMapper.fromId( carDTO.getCarPriceId() ) );
        car_.setProvider( providerMapper.fromId( carDTO.getProviderId() ) );
        car_.setId( carDTO.getId() );
        car_.setCapacity( carDTO.getCapacity() );

        return car_;
    }

    private Long carProviderId(Car car) {

        if ( car == null ) {
            return null;
        }
        Provider provider = car.getProvider();
        if ( provider == null ) {
            return null;
        }
        Long id = provider.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long carCarPriceId(Car car) {

        if ( car == null ) {
            return null;
        }
        CarPrice carPrice = car.getCarPrice();
        if ( carPrice == null ) {
            return null;
        }
        Long id = carPrice.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
