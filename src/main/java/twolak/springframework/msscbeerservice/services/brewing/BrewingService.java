package twolak.springframework.msscbeerservice.services.brewing;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twolak.springframework.msscbeerservice.config.JmsConfig;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.common.events.BrewBeerEvent;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;
import twolak.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import twolak.springframework.msscbeerservice.web.mappers.BeerMapper;

/**
 *
 * @author twolak
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;
    
    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = this.beerRepository.findAll();
        
        beers.forEach(beer -> {
            Integer invQOH = this.beerInventoryService.getOnHandInventory(beer.getId());
            
            log.debug("Min on hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQOH);
            
            if (invQOH <= beer.getMinOnHand()) {
                this.jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(this.beerMapper.beerToBeerDto(beer)));
            }
        });
    }
    
}
