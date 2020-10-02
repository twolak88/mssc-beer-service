package twolak.springframework.msscbeerservice.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;
import twolak.springframework.msscbeerservice.services.BeerService;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.web.model.BeerStyleEnum;

/**
 *
 * @author twolak
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BeerServiceImplIT {
    
    private static final UUID BEER_ID = UUID.randomUUID();
    private static final String BEER_NAME = "Beer";
    private static final String BEER_STYLE = BeerStyleEnum.PORTER.toString();
    private static final double PRICE = 5.32;
    private static final String UPC = "0631234200036";
    
    @MockBean
    private BeerRepository beerRepository;
    
    @Autowired
    private BeerService beerService;
    
    private Beer validBeer;
    
    @BeforeEach
    public void setUp() {
        this.validBeer = Beer.builder()
                .id(BEER_ID)
                .beerName(BEER_NAME)
                .beerStyle(BEER_STYLE)
                .upc(UPC)
                .price(BigDecimal.valueOf(PRICE))
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .build();
    }
    

    @Test
    public void testFindById() {
        BDDMockito.given(this.beerRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(Optional.of(this.validBeer));
        
        BeerDto foundBeerDto = this.beerService.findById(BEER_ID);
        
        assertNotNull(foundBeerDto);
        assertEquals(BEER_NAME, foundBeerDto.getBeerName());
        assertEquals(BeerStyleEnum.PORTER, foundBeerDto.getBeerStyle());
        assertEquals(BigDecimal.valueOf(PRICE), foundBeerDto.getPrice());
        assertEquals(UPC, foundBeerDto.getUpc());
        assertEquals(BEER_ID, foundBeerDto.getId());
        BDDMockito.then(this.beerRepository).should(Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    public void testSaveNewBeer() {
        BeerDto beerDto = BeerDto.builder()
                .beerName(BEER_NAME)
                .beerStyle(BeerStyleEnum.PORTER)
                .upc(UPC)
                .price(BigDecimal.valueOf(PRICE))
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        
        
        BDDMockito.given(this.beerRepository.save(ArgumentMatchers.any(Beer.class))).willReturn(validBeer);
        
        BeerDto savedBeerDto = this.beerService.saveNewBeer(beerDto);
        
        assertNotNull(savedBeerDto);
        assertEquals(BEER_NAME, savedBeerDto.getBeerName());
        assertEquals(BeerStyleEnum.PORTER, savedBeerDto.getBeerStyle());
        assertEquals(BigDecimal.valueOf(PRICE), savedBeerDto.getPrice());
        assertEquals(UPC, savedBeerDto.getUpc());
        assertEquals(BEER_ID, savedBeerDto.getId());
        BDDMockito.then(this.beerRepository).should(Mockito.times(1)).save(ArgumentMatchers.any(Beer.class));
    }

    @Test
    public void testUpdateBeer() {
        BeerDto beerDto = BeerDto.builder()
                .beerName("Warka")
                .beerStyle(BeerStyleEnum.PORTER)
                .upc(UPC)
                .price(BigDecimal.valueOf(PRICE))
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        BDDMockito.given(this.beerRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(Optional.of(this.validBeer));
        BDDMockito.given(this.beerRepository.save(ArgumentMatchers.any(Beer.class))).willReturn(validBeer);
        
        BeerDto updatedBeerDto = this.beerService.updateBeer(BEER_ID, beerDto);
        
        assertNotNull(updatedBeerDto);
        assertEquals("Warka", updatedBeerDto.getBeerName());
        assertEquals(BeerStyleEnum.PORTER, updatedBeerDto.getBeerStyle());
        assertEquals(BigDecimal.valueOf(PRICE), updatedBeerDto.getPrice());
        assertEquals(UPC, updatedBeerDto.getUpc());
        assertEquals(BEER_ID, updatedBeerDto.getId());
        BDDMockito.then(this.beerRepository).should(Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    public void testDeleteBeer() {
        this.beerService.deleteBeer(BEER_ID);
        
        BDDMockito.then(this.beerRepository).should(Mockito.times(1)).deleteById(ArgumentMatchers.any(UUID.class));
    }
    
}
