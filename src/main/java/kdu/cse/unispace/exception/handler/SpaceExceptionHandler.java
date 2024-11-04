package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.exception.space.SpaceNotFoundException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpaceExceptionHandler {

    @ExceptionHandler({SpaceNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handleSpaceNotFoundException(SpaceNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
