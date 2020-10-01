package twolak.springframework.msscbeerservice.services;

import java.util.UUID;
import twolak.springframework.msscbeerservice.web.model.BeerDto;

/**
 *
 * @author twolak
 */
public interface BeerService {
    BeerDto findById(UUID beerId);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
    void deleteBeer(UUID beerId);
}
