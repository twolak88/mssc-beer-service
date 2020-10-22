package twolak.springframework.msscbeerservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.brewery.model.BeerDto;

/**
 *
 * @author twolak
 */
@Mapper(uses = {DateMapper.class})
@DecoratedWith(value = BeerMapperDecorator.class)
public interface BeerMapper {
    
    BeerDto beerToBeerDto(Beer beer);
    
    BeerDto beerToBeerDtoWithInventoryOnHand(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
