package kdu.cse.unispace.responsedto.schedule.todo;

import kdu.cse.unispace.domain.space.schedule.ToDo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToDoDto {
    private Long toDoId;
    private String description;
    private Boolean completed;
    private LocalDateTime date;
    private boolean active;

    public ToDoDto(ToDo toDo) {
        toDoId = toDo.getId();
        description = toDo.getDescription();
        completed = toDo.getCompleted();
        date = toDo.getDate();
        active = toDo.isActive();
    }
}
