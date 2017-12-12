package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Location;
import org.carsonrent.rentals.domain.Provider;
import org.carsonrent.rentals.service.dto.ProviderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class ProviderMapperImpl implements ProviderMapper {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<Provider> toEntity(List<ProviderDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Provider> list = new ArrayList<Provider>();
        for ( ProviderDTO providerDTO : dtoList ) {
            list.add( toEntity( providerDTO ) );
        }

        return list;
    }

    @Override
    public List<ProviderDTO> toDto(List<Provider> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProviderDTO> list = new ArrayList<ProviderDTO>();
        for ( Provider provider : entityList ) {
            list.add( toDto( provider ) );
        }

        return list;
    }

    @Override
    public ProviderDTO toDto(Provider provider) {
        if ( provider == null ) {
            return null;
        }

        ProviderDTO providerDTO_ = new ProviderDTO();

        providerDTO_.setLocationId( providerLocationId( provider ) );
        providerDTO_.setId( provider.getId() );
        providerDTO_.setName( provider.getName() );

        return providerDTO_;
    }

    @Override
    public Provider toEntity(ProviderDTO providerDTO) {
        if ( providerDTO == null ) {
            return null;
        }

        Provider provider_ = new Provider();

        provider_.setLocation( locationMapper.fromId( providerDTO.getLocationId() ) );
        provider_.setId( providerDTO.getId() );
        provider_.setName( providerDTO.getName() );

        return provider_;
    }

    private Long providerLocationId(Provider provider) {

        if ( provider == null ) {
            return null;
        }
        Location location = provider.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
