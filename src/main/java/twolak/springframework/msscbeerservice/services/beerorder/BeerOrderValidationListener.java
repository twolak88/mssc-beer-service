package twolak.springframework.msscbeerservice.services.beerorder;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import twolak.springframework.brewery.model.events.ValidateBeerOrderRequest;
import twolak.springframework.brewery.model.events.ValidateBeerOrderResult;
import twolak.springframework.msscbeerservice.config.JmsConfig;

/**
 *
 * @author twolak
 */
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {
    
    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;
    
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest validateBeerOrderRequest) {
        Boolean isValid = this.beerOrderValidator.validateBeerOrder(validateBeerOrderRequest.getBeerOrderDto());
        this.jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, ValidateBeerOrderResult.builder()
                        .orderId(validateBeerOrderRequest.getBeerOrderDto().getId())
                        .isValid(isValid)
                        .build());
    }
}
