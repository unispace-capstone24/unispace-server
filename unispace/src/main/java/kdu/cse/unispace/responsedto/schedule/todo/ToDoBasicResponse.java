package kdu.cse.unispace.responsedto.schedule.todo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToDoBasicResponse {
    private Long id;
    private Integer status;
    private String message;

    public ToDoBasicResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
