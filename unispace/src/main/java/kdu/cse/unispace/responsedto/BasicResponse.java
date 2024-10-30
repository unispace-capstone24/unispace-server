package kdu.cse.unispace.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse<T> {
    private Integer count;
    private String message;
    private T data;
}
