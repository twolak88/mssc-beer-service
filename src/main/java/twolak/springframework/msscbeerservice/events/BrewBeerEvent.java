package twolak.springframework.msscbeerservice.events;

import lombok.NoArgsConstructor;
import twolak.springframework.msscbeerservice.web.model.BeerDto;

/**
 *
 * @author twolak
 */
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    private static final long serialVersionUID = 7330709215067000572L;
    
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
