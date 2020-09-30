package twolak.springframework.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.web.model.BeerDto;

/**
 *
 * @author twolak
 */
@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    
    BeerDto beerToBeerDto(Beer beer);

    Beer beerToBeerDto(BeerDto beerDto);
}
