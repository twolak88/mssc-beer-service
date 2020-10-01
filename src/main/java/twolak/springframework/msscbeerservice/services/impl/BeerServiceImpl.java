package twolak.springframework.msscbeerservice.services.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;
import twolak.springframework.msscbeerservice.services.BeerService;
import twolak.springframework.msscbeerservice.web.controller.NotFoundException;
import twolak.springframework.msscbeerservice.web.mappers.BeerMapper;
import twolak.springframework.msscbeerservice.web.model.BeerDto;

/**
 *
 * @author twolak
 */
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{
    
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto findById(UUID beerId) {
        return this.beerMapper.beerToBeerDto(
                this.beerRepository.findById(beerId)
                    .orElseThrow(() -> new NotFoundException("Beer not found"))
        );
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return this.beerMapper.beerToBeerDto(
                this.beerRepository.save(this.beerMapper.beerDtoToBeer(beerDto))
        );
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = this.beerRepository.findById(beerId)
                .orElseThrow(() -> new NotFoundException("Beer not found"));
        
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().toString());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        return this.beerMapper.beerToBeerDto(this.beerRepository.save(beer));
    }

    @Override
    public void deleteBeer(UUID beerId) {
        this.beerRepository.deleteById(beerId);
    }
    
}
