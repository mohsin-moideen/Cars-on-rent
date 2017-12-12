package org.carsonrent.rentals.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.carsonrent.rentals.domain.Bookings;
import org.carsonrent.rentals.domain.Provider;
import org.carsonrent.rentals.domain.User;
import org.carsonrent.rentals.service.dto.BookingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-09-11T21:35:55+0530",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class BookingsMapperImpl implements BookingsMapper {

    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Bookings> toEntity(List<BookingsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Bookings> list = new ArrayList<Bookings>();
        for ( BookingsDTO bookingsDTO : dtoList ) {
            list.add( toEntity( bookingsDTO ) );
        }

        return list;
    }

    @Override
    public List<BookingsDTO> toDto(List<Bookings> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BookingsDTO> list = new ArrayList<BookingsDTO>();
        for ( Bookings bookings : entityList ) {
            list.add( toDto( bookings ) );
        }

        return list;
    }

    @Override
    public BookingsDTO toDto(Bookings bookings) {
        if ( bookings == null ) {
            return null;
        }

        BookingsDTO bookingsDTO_ = new BookingsDTO();

        bookingsDTO_.setUserId( bookingsUserId( bookings ) );
        bookingsDTO_.setProviderId( bookingsProviderId( bookings ) );
        bookingsDTO_.setId( bookings.getId() );
        bookingsDTO_.setStartDate( bookings.getStartDate() );
        bookingsDTO_.setEndDate( bookings.getEndDate() );
        bookingsDTO_.setStatus( bookings.getStatus() );

        return bookingsDTO_;
    }

    @Override
    public Bookings toEntity(BookingsDTO bookingsDTO) {
        if ( bookingsDTO == null ) {
            return null;
        }

        Bookings bookings_ = new Bookings();

        bookings_.setProvider( providerMapper.fromId( bookingsDTO.getProviderId() ) );
        bookings_.setUser( userMapper.userFromId( bookingsDTO.getUserId() ) );
        bookings_.setId( bookingsDTO.getId() );
        bookings_.setStartDate( bookingsDTO.getStartDate() );
        bookings_.setEndDate( bookingsDTO.getEndDate() );
        bookings_.setStatus( bookingsDTO.getStatus() );

        return bookings_;
    }

    private Long bookingsUserId(Bookings bookings) {

        if ( bookings == null ) {
            return null;
        }
        User user = bookings.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long bookingsProviderId(Bookings bookings) {

        if ( bookings == null ) {
            return null;
        }
        Provider provider = bookings.getProvider();
        if ( provider == null ) {
            return null;
        }
        Long id = provider.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
