package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccessExceptionHandler { //접근권한에 관한 예외 핸들러
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorBasicResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
