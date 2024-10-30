package kdu.cse.unispace.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//request에 필수정보가 없는 예외
public class RequiredValueMissingException extends RuntimeException {

    public RequiredValueMissingException(String message) {
        super(message);
    }
}
