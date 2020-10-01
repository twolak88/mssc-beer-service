package twolak.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;

/**
 *
 * @author twolak
 */
@Component
public class BeerLoader implements CommandLineRunner {
    
    private static final String BEER_1_UPC = "0631234200036";
    private static final String BEER_2_UPC = "0631234300019";
    private static final String BEER_3_UPC = "0083783375213";
    private static final String BEER_4_UPC = "0083783375214";
    private static final String BEER_5_UPC = "0083783375215";
    
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
            CreateBeer("Warka", "STOUT", BEER_1_UPC, 3.32, 12, 200);
            CreateBeer("Debowe", "LAGER", BEER_2_UPC, 3.55, 12, 222);
            CreateBeer("Zywiec", "PORTER", BEER_3_UPC, 3.43, 11, 232);
            CreateBeer("Okocim", "WHEAT", BEER_4_UPC, 3.67, 10, 212);
            CreateBeer("Kozel", "LAGER", BEER_5_UPC, 4.22, 11, 223);
        }
    }

    private void CreateBeer(String beerName, String beerStyle, String upc, double price, Integer minOnHand, Integer qualityToBrew) {
        this.beerRepository.save(Beer.builder().beerName(beerName)
                .beerStyle(beerStyle)
                .upc(upc)
                .price(BigDecimal.valueOf(price))
                .minOnHand(minOnHand)
                .qualityToBrew(qualityToBrew)
                .build());
    }
    
}
