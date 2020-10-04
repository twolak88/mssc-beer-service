package twolak.springframework.msscbeerservice.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import twolak.springframework.msscbeerservice.web.model.BeerDto;

/**
 *
 * @author twolak
 */
public abstract class BeerMapperDecorator implements BeerMapper{
    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;
    
    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }
    
    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }
    
    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto beerDto = this.beerMapper.beerToBeerDto(beer);
        beerDto.setQuantityOnHand(this.beerInventoryService.getOnHandInventory(beer.getId()));
        return beerDto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return this.beerMapper.beerDtoToBeer(beerDto);
    }
}
