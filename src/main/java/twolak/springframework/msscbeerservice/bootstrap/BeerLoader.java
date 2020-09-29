package twolak.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerReporsitory;

/**
 *
 * @author twolak
 */
@Component
public class BeerLoader implements CommandLineRunner {
    
    private final BeerReporsitory beerReporsitory;

    public BeerLoader(BeerReporsitory beerReporsitory) {
        this.beerReporsitory = beerReporsitory;
    }
    
    @Override
    public void run(String... args) throws Exception {
        loadBeers();
    }

    private void loadBeers() {
        if (this.beerReporsitory.count() == 0) {
            CreateBeer("Warka", "STOUT", 123456781L, 3.32, 12, 200);
            CreateBeer("Debowe", "LAGER", 123456782L, 3.55, 12, 222);
            CreateBeer("Zywiec", "PORTER", 123456783L, 3.43, 11, 232);
            CreateBeer("Okocim", "WHEAT", 123456784L, 3.67, 10, 212);
            CreateBeer("Kozel", "LAGER", 123456785L, 4.22, 11, 223);
        }
    }

    private void CreateBeer(String beerName, String beerStyle, Long upc, double price, Integer minOnHand, Integer qualityToBrew) {
        this.beerReporsitory.save(Beer.builder().beerName(beerName)
                .beerStyle(beerStyle)
                .upc(upc)
                .price(BigDecimal.valueOf(price))
                .minOnHand(minOnHand)
                .qualityToBrew(qualityToBrew)
                .build());
    }
    
}
