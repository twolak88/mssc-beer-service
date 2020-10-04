package twolak.springframework.msscbeerservice.services;

import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.web.model.BeerPagedList;
import twolak.springframework.msscbeerservice.web.model.BeerStyleEnum;

/**
 *
 * @author twolak
 */
public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);
    BeerDto findById(UUID beerId);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
    void deleteBeer(UUID beerId);
}
