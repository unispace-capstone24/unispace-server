package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.exception.page.PageDeletionNotAllowedException;
import kdu.cse.unispace.exception.page.PageNotFoundException;
import kdu.cse.unispace.exception.page.PageNotInSpaceException;
import kdu.cse.unispace.exception.page.PageRestoreNotCurrentPageIdException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PageExceptionHandler {
    @ExceptionHandler({PageNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handlePageNotFoundException(PageNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({PageDeletionNotAllowedException.class})
    public ResponseEntity<ErrorBasicResponse> handlePageDeletionNotAllowedException(PageDeletionNotAllowedException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({PageNotInSpaceException.class})
    public ResponseEntity<ErrorBasicResponse> handlePageNotInSpaceException(PageNotInSpaceException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PageRestoreNotCurrentPageIdException.class})
    public ResponseEntity<ErrorBasicResponse> handlePageRestoreNotCurrentPageIdException(PageRestoreNotCurrentPageIdException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
