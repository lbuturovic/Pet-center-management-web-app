package ba.unsa.etf.pnwt.petcenter.Exceptions;

import ba.unsa.etf.pnwt.petcenter.Exceptions.ApiError;
import ba.unsa.etf.pnwt.petcenter.Exceptions.ErrorDetails;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiError.class)
    public ResponseEntity<?> handleBadRequestException(ApiError apiError){
        ErrorDetails errorDetails = new ErrorDetails(apiError.getError(),apiError.getMessage());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<?> handlePropertyValueException(PropertyValueException apiError){
        ErrorDetails errorDetails = new ErrorDetails("Missing property",apiError.getPropertyName()+" is missing");
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
