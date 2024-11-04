package kdu.cse.unispace.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorBasicResponse {
    private int status;
    private String message;
}
