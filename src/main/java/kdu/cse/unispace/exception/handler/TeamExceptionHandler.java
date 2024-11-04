package kdu.cse.unispace.exception.handler;

import kdu.cse.unispace.exception.team.TeamNotFoundException;
import kdu.cse.unispace.responsedto.ErrorBasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TeamExceptionHandler {

    @ExceptionHandler({TeamNotFoundException.class})
    public ResponseEntity<ErrorBasicResponse> handleTeamNotFoundException(TeamNotFoundException ex) {
        ErrorBasicResponse errorResponse = new ErrorBasicResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
