package twolak.springframework.msscbeerservice.services.beerorder;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twolak.springframework.brewery.model.BeerOrderDto;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;

/**
 *
 * @author twolak
 */
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {
    
    private final BeerRepository beerRepository;
    
    public Boolean validateBeerOrder(BeerOrderDto beerOrderDto) {
        
        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(orderLine -> {
            if(this.beerRepository.findByUpc(orderLine.getUpc()) == null) {
                beersNotFound.incrementAndGet();
            }
        });
        
        return beersNotFound.get() == 0;
    }
}
