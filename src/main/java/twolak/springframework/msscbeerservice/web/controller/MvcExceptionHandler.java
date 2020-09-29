package twolak.springframework.msscbeerservice.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author twolak
 */
@ControllerAdvice
public class MvcExceptionHandler {
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException constraintViolationException) {
        List<String> errorsList = new ArrayList<>(constraintViolationException.getConstraintViolations().size());
        constraintViolationException.getConstraintViolations().forEach(constraintViolation -> {
            errorsList.add(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List> handleBindException(BindException bindException) {
        return new ResponseEntity<>(bindException.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
