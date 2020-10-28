package twolak.springframework.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import twolak.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;

/**
 *
 * @author twolak
 */
@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BeerInventoryServiceFeign implements BeerInventoryService {
    
    private final BeerInventoryServiceFeignClient beerInventoryServiceFeignClient;

    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.debug("Calling Inventory Service - BeerId: " + beerId.toString());
        ResponseEntity<List<BeerInventoryDto>> responseEntity = this.beerInventoryServiceFeignClient.getOnHandInventory(beerId);
        
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
        
        log.debug("BeerId: " + beerId + " On hand is: " + onHand);
        
        return onHand;
    }
    
}
