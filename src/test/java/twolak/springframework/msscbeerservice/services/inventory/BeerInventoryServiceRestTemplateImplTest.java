package twolak.springframework.msscbeerservice.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twolak.springframework.msscbeerservice.bootstrap.BeerLoader;

/**
 *
 * @author twolak
 */
@Slf4j
@Disabled
@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {
    
    @Autowired
    BeerInventoryService beerInventoryService;
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void testGetOnHandInventory() {
//        Integer quantityOnHand = this.beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
//        log.debug("Quantity on hand: " + quantityOnHand);
//        assertEquals(60, quantityOnHand);
    }
    
}
