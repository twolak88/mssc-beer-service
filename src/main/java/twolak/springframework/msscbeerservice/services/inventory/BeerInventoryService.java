package twolak.springframework.msscbeerservice.services.inventory;

import java.util.UUID;

/**
 *
 * @author twolak
 */
public interface BeerInventoryService {
    Integer getOnHandInventory(UUID beerId);
}
