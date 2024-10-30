package kdu.cse.unispace.requestdto.schedule.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoCompletedUpdateDto {
    private Boolean completed;
}
