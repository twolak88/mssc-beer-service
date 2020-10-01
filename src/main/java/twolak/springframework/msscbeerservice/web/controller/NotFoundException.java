package twolak.springframework.msscbeerservice.web.controller;

/**
 *
 * @author twolak
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException() {
        super();
    }
    
    public NotFoundException(String message) {
        super(message);
    }
}
