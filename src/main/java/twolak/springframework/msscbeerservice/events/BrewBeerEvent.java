package twolak.springframework.msscbeerservice.events;

import twolak.springframework.msscbeerservice.web.model.BeerDto;

/**
 *
 * @author twolak
 */
public class BrewBeerEvent extends BeerEvent {

    private static final long serialVersionUID = 7330709215067000572L;
    
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
