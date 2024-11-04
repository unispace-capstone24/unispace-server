package kdu.cse.unispace.exception.handler;


import kdu.cse.unispace.exception.jwt.TokenInvalidateException;
import kdu.cse.unispace.exception.jwt.TokenNotFoundException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionHandler {


    @ExceptionHandler({ TokenInvalidateException.class})
    public ResponseEntity<ErrorBasicResponse> handleTokenInvalidateException(TokenInvalidateException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ TokenNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
