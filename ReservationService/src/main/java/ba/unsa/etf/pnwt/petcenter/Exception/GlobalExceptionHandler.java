package ba.unsa.etf.pnwt.petcenter.Exception;
import org.hibernate.PersistentObjectException;
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

    @ExceptionHandler(PersistentObjectException.class)
    public ResponseEntity<?> handlePersistentObjectException(PersistentObjectException apiError){
        ErrorDetails errorDetails = new ErrorDetails("Persistent Object Exception",apiError.getMessage());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException apiError){
        ErrorDetails errorDetails = new ErrorDetails("Null Pointer Exception",apiError.getMessage());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException apiError){
        ErrorDetails errorDetails = new ErrorDetails("Not found error",apiError.getMessage());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}