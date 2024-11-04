package kdu.cse.unispace.requestdto.schedule.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoRequestDto {
    private String description;
    private Boolean completed;
}
