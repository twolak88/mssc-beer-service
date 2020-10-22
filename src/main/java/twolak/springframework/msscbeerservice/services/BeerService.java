package twolak.springframework.msscbeerservice.services;

import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import twolak.springframework.brewery.model.BeerDto;
import twolak.springframework.brewery.model.BeerPagedList;
import twolak.springframework.brewery.model.BeerStyleEnum;

/**
 *
 * @author twolak
 */
public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);
    BeerDto findById(UUID beerId, Boolean showInventoryOnHand);
    BeerDto findByUpc(String upc);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
    void deleteBeer(UUID beerId);
}
