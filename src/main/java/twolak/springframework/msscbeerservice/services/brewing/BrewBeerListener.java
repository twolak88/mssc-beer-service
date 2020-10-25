package twolak.springframework.msscbeerservice.services.brewing;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twolak.springframework.brewery.model.BeerDto;
import twolak.springframework.brewery.model.events.BrewBeerEvent;
import twolak.springframework.brewery.model.events.NewInventoryEvent;
import twolak.springframework.msscbeerservice.config.JmsConfig;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;

/**
 *
 * @author twolak
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent) {
        BeerDto beerDto = brewBeerEvent.getBeerDto();

        Optional<Beer> beerOptional = this.beerRepository.findById(beerDto.getId());
        beerOptional.ifPresentOrElse((beer) -> {
            beerDto.setQuantityOnHand(beer.getQuantityToBrew());

            log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

            NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
            jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
        }, () -> {
            String errorMessage = "Beer not found! Id: " + beerDto.getId();
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        });
    }
}
