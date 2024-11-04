package kdu.cse.unispace.responsedto.schedule.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoEasyResponse {
    private Long easyToDoId;
    private Integer status;
    private String message;
}
