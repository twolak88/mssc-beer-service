package twolak.springframework.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import twolak.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;

/**
 *
 * @author twolak
 */
@Profile("!local-discovery")
@Slf4j
@ConfigurationProperties(prefix = "tw.brewery", ignoreUnknownFields = false)
@Service
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private final RestTemplate restTemplate;
    private String beerInventoryServiceHost;

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.debug("Calling Inventory Service...");
        
        ResponseEntity<List<BeerInventoryDto>> responseEntity = this.restTemplate.exchange(this.beerInventoryServiceHost + INVENTORY_PATH, 
                HttpMethod.GET, null, new ParameterizedTypeReference<List<BeerInventoryDto>>(){}, beerId);
        return Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
    }
    
}
