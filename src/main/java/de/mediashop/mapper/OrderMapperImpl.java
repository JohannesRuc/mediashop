package de.mediashop.mapper;

import de.mediashop.model.OrderDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-08T09:14:22+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9"
)
public class OrderMapperImpl {

    public OrderDto toDto(Map<String, Object> row) {
        if ( row == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        if ( row.get( "id" ) != null ) {
            orderDto.setId( String.valueOf( row.get( "id" ) ) );
        }
        if ( row.get( "status" ) != null ) {
            orderDto.setStatus( String.valueOf( row.get( "status" ) ) );
        }
        if ( row.get( "total_amount" ) != null ) {
            orderDto.setTotalAmount( new BigDecimal( String.valueOf( row.get( "total_amount" ) ) ) );
        }
        if ( row.get( "created_at" ) != null ) {
            orderDto.setCreatedAt( Instant.parse( String.valueOf( row.get( "created_at" ) ) ) );
        }

        return orderDto;
    }
}
