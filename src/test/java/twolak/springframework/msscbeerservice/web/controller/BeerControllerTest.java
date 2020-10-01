package twolak.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import twolak.springframework.msscbeerservice.web.model.BeerDto;
import twolak.springframework.msscbeerservice.web.model.BeerStyleEnum;
import twolak.springframework.msscbeerservice.services.BeerService;

/**
 *
 * @author twolak
 */
@WebMvcTest(controllers = {BeerController.class})
@ComponentScan(basePackages = {"twolak.springframework.msscbeerservice.web.mappers", "twolak.springframework.msscbeerservice.services"})
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BeerDto validBeerDto;
    
    @MockBean
    private BeerService beerService;
    
    @BeforeEach
    public void setUp() {
        this.validBeerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer")
                .beerStyle(BeerStyleEnum.PORTER)
                .upc("0631234200036")
                .price(BigDecimal.valueOf(5.32))
                .build();
    }

    @Test
    public void testGetBeer() throws Exception {
        BDDMockito.given(this.beerService.findById(ArgumentMatchers.any(UUID.class))).willReturn(this.validBeerDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get(BeerController.BASE_URL + "/" + this.validBeerDto.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(this.validBeerDto.getId().toString())));
        BDDMockito.then(this.beerService).should(Mockito.times(1)).findById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    public void testSaveNewBeer() throws Exception {
        BeerDto beerDto = validBeerDto;
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        
        BDDMockito.given(this.beerService.saveNewBeer(ArgumentMatchers.any(BeerDto.class))).willReturn(beerDto);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post(BeerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        BDDMockito.then(this.beerService).should(Mockito.times(1)).saveNewBeer(ArgumentMatchers.any(BeerDto.class));
    }

    @Test
    public void testUpdateBeer() throws Exception {
        BeerDto beerDto = validBeerDto;
        beerDto.setId(null);
        String beerDtoJson = this.objectMapper.writeValueAsString(beerDto);
        
        BDDMockito.given(this.beerService.updateBeer(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BeerDto.class))).willReturn(beerDto);

        this.mockMvc.perform(MockMvcRequestBuilders.put(BeerController.BASE_URL + "/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        BDDMockito.then(this.beerService).should(Mockito.times(1)).updateBeer(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BeerDto.class));
    }

    @Test
    public void testDeleteBeer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(BeerController.BASE_URL + "/" + this.validBeerDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        BDDMockito.then(this.beerService).should(Mockito.times(1)).deleteBeer(ArgumentMatchers.any(UUID.class));
    }
}
