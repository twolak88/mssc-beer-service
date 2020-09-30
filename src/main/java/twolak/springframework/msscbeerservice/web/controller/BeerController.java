package twolak.springframework.msscbeerservice.web.controller;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twolak.springframework.msscbeerservice.web.mappers.BeerMapper;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;

/**
 *
 * @author twolak
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.BASE_URL)
public class BeerController {
    public static final String BASE_URL = "/api/v1/beer";
    private static final String BEER_ID = "beerId";
    
    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;
    
    @GetMapping("{" + BEER_ID + "}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable(BEER_ID) UUID beerId) {
        return new ResponseEntity<>(this.beerMapper.beerToBeerDto(this.beerRepository.findById(beerId)
                .orElseThrow(() -> {
                    throw new RuntimeException("Beer not found");
                })), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto) {
        this.beerRepository.save(this.beerMapper.beerDtoToBeer(beerDto));
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @PutMapping("{" + BEER_ID + "}")
    public ResponseEntity updateBeer(@PathVariable(BEER_ID) UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        this.beerRepository.findById(beerId).ifPresent(beer -> {
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle().toString());
            beer.setPrice(beerDto.getPrice());
            beer.setUpc(beerDto.getUpc());
            
            this.beerRepository.save(beer);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("{" + BEER_ID + "}")
    public ResponseEntity deleteBeer(@PathVariable(BEER_ID) UUID beerId) {
        this.beerRepository.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
