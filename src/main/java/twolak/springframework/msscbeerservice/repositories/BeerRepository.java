package twolak.springframework.msscbeerservice.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.brewery.model.BeerStyleEnum;

/**
 *
 * @author twolak
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);
    Page<Beer> findAllByBeerName(String beerName, PageRequest pageRequest);
    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, PageRequest pageRequest);
    Optional<Beer> findByUpc(String upc);
}
