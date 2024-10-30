package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.exception.TrashCan.TrashCanNotFoundException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrashCanExceptionHandler {
    @ExceptionHandler({ TrashCanNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handleTrashCanFoundException(TrashCanNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
