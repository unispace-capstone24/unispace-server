package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.exception.RequiredValueMissingException;
import kdu.cse.unispace.exception.chat.RoomNotFoundException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoomExceptionHandler {
    @ExceptionHandler({ RoomNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handleToDoNotFoundException(RoomNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RequiredValueMissingException.class)
    public ResponseEntity<ErrorBasicResponse> handleRequiredValueMissingException(RequiredValueMissingException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
