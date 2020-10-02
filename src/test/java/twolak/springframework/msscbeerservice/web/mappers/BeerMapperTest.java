package twolak.springframework.msscbeerservice.web.mappers;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.web.model.BeerStyleEnum;

/**
 *
 * @author twolak
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BeerMapperTest {
    
    private static final UUID BEER_ID = UUID.randomUUID();
    private static final String BEER_UPC = "0631234200036";
    private static final double BEER_PRICE = 5.32;
    private static final BeerStyleEnum BEER_STYLE = BeerStyleEnum.PORTER;
    private static final String BEER_NAME = "Beer";
    
    @Autowired
    private BeerMapper beerMapper;
    
    @BeforeEach
    void setUp() throws Exception {
    }
    
    @Test
    public void testBeerToBeerDto() {
        Beer beer = Beer.builder()
                .id(BEER_ID)
                .beerName(BEER_NAME)
                .beerStyle(BEER_STYLE.toString())
                .upc(BEER_UPC)
                .price(BigDecimal.valueOf(BEER_PRICE))
                .build();
        BeerDto beerDto = this.beerMapper.beerToBeerDto(beer);
        
        assertEquals(BEER_ID, beerDto.getId());
        assertEquals(BEER_UPC, beerDto.getUpc());
        assertEquals(BigDecimal.valueOf(BEER_PRICE), beerDto.getPrice());
        assertEquals(BEER_STYLE, beerDto.getBeerStyle());
        assertEquals(BEER_NAME, beerDto.getBeerName());
    }

    @Test
    public void testBeerDtoToBeer() {
        BeerDto beerDto = BeerDto.builder()
                .id(BEER_ID)
                .beerName(BEER_NAME)
                .beerStyle(BEER_STYLE)
                .upc(BEER_UPC)
                .price(BigDecimal.valueOf(BEER_PRICE))
                .build();
        Beer beer = this.beerMapper.beerDtoToBeer(beerDto);
        
        assertEquals(BEER_ID, beer.getId());
        assertEquals(BEER_UPC, beer.getUpc());
        assertEquals(BigDecimal.valueOf(BEER_PRICE), beer.getPrice());
        assertEquals(BEER_STYLE.toString(), beer.getBeerStyle());
        assertEquals(BEER_NAME, beer.getBeerName());
    }
}
