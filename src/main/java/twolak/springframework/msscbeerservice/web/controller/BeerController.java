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
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.services.BeerService;

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
    
    private final BeerService beerService;
    
    @GetMapping("{" + BEER_ID + "}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable(BEER_ID) UUID beerId) {
        return new ResponseEntity<>(this.beerService.findById(beerId), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(this.beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }
    
    @PutMapping("{" + BEER_ID + "}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable(BEER_ID) UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(this.beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("{" + BEER_ID + "}")
    public ResponseEntity deleteBeer(@PathVariable(BEER_ID) UUID beerId) {
        this.beerService.deleteBeer(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
