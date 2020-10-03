package twolak.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;

/**
 *
 * @author twolak
 */
@Slf4j
//@Component
public class BeerLoader implements CommandLineRunner {
    
    private static final String BEER_1_UPC = "0631234200036";
    private static final String BEER_2_UPC = "0631234300019";
    private static final String BEER_3_UPC = "0083783375213";
    private static final String BEER_4_UPC = "0083783375214";
    private static final String BEER_5_UPC = "0083783375215";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");
    
    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        loadBeers();
    }

    private void loadBeers() {
        if (this.beerRepository.count() == 0) {
            CreateBeer(BEER_1_UUID, "Warka", "IPA", BEER_1_UPC, 3.32, 12, 200);
            CreateBeer(BEER_2_UUID, "Debowe", "LAGER", BEER_2_UPC, 3.55, 12, 222);
            CreateBeer(BEER_3_UUID, "Zywiec", "PORTER", BEER_3_UPC, 3.43, 11, 232);
            CreateBeer(null, "Okocim", "WHEAT", BEER_4_UPC, 3.67, 10, 212);
            CreateBeer(null, "Kozel", "LAGER", BEER_5_UPC, 4.22, 11, 223);
            log.debug("Beers loaded count: " + this.beerRepository.count());
        }
    }

    private void CreateBeer(UUID id, String beerName, String beerStyle, String upc, double price, Integer minOnHand, Integer quantityToBrew) {
        this.beerRepository.save(Beer.builder().beerName(beerName)
                .id(id)
                .beerStyle(beerStyle)
                .upc(upc)
                .price(BigDecimal.valueOf(price))
                .minOnHand(minOnHand)
                .quantityToBrew(quantityToBrew)
                .build());
    }
    
}
