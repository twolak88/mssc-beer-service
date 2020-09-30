package twolak.springframework.msscbeerservice.repositories;

import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import twolak.springframework.msscbeerservice.domain.Beer;

/**
 *
 * @author twolak
 */
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    
}
