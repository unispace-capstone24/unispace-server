package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.exception.category.CategoryActiveException;
import kdu.cse.unispace.exception.category.CategoryNotFoundException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CategoryActiveException.class})
    public ResponseEntity<ErrorBasicResponse> handleCategoryActiveException(CategoryActiveException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
