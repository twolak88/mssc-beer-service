package twolak.springframework.msscbeerservice.services.inventory;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import twolak.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;

/**
 *
 * @author twolak
 */
@FeignClient(name = "beer-inventory-service")
public interface BeerInventoryServiceFeignClient {
    
    @GetMapping(value = BeerInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable("beerId") UUID beerId);
}
