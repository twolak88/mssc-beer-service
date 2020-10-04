package twolak.springframework.msscbeerservice.services.impl;

import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import twolak.springframework.msscbeerservice.domain.Beer;
import twolak.springframework.msscbeerservice.repositories.BeerRepository;
import twolak.springframework.msscbeerservice.services.BeerService;
import twolak.springframework.msscbeerservice.web.controller.NotFoundException;
import twolak.springframework.msscbeerservice.web.mappers.BeerMapper;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.web.model.BeerPagedList;
import twolak.springframework.msscbeerservice.web.model.BeerStyleEnum;

/**
 *
 * @author twolak
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{
    
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
//        log.debug("BeerServiceImpl.listBeers: Cache test...");
        Page<Beer> beerPage;
        
        if (!StringUtils.isEmpty(beerName) && beerStyle != null) {
            beerPage = this.beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && beerStyle == null) {
            beerPage = this.beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && beerStyle != null) {
            beerPage = this.beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = this.beerRepository.findAll(pageRequest);
        }
        
        return new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beer -> {
                    if (showInventoryOnHand.booleanValue()) {
                        return this.beerMapper.beerToBeerDtoWithInventoryOnHand(beer);
                    }
                    return this.beerMapper.beerToBeerDto(beer);
                })
                .collect(Collectors.toList()),
                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());
    }
    
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto findById(UUID beerId, Boolean showInventoryOnHand) {
//        log.debug("BeerServiceImpl.findById for id " + beerId + " cache test");
        Beer foundBeer = this.beerRepository.findById(beerId).orElseThrow(() -> new NotFoundException("Beer not found"));
        if (showInventoryOnHand.booleanValue()){
            return this.beerMapper.beerToBeerDtoWithInventoryOnHand(foundBeer);
        }
        return this.beerMapper.beerToBeerDto(foundBeer);
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
