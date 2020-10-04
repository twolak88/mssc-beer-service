package twolak.springframework.msscbeerservice.web.controller;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.services.BeerService;
import twolak.springframework.msscbeerservice.web.model.BeerPagedList;
import twolak.springframework.msscbeerservice.web.model.BeerStyleEnum;

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
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    
    private final BeerService beerService;
    
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return new ResponseEntity<>(this.beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand), HttpStatus.OK);
    }
    
    @GetMapping("{" + BEER_ID + "}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable(BEER_ID) UUID beerId,
                                           @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(this.beerService.findById(beerId, showInventoryOnHand), HttpStatus.OK);
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
