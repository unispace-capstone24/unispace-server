package kdu.cse.unispace.responsedto.schedule.easy;

import kdu.cse.unispace.domain.space.schedule.Category;
import kdu.cse.unispace.domain.space.schedule.EasyToDo;
import kdu.cse.unispace.domain.space.schedule.ToDo;
import kdu.cse.unispace.repository.ToDoRepository;
import kdu.cse.unispace.responsedto.schedule.todo.ToDoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyCategory {
    private Long id;
    private String title;
    private List<EasyToDoDto> todos = new ArrayList<>();
    private String color;

    public EasyCategory(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.color = category.getColor();
        if (category.getEasyToDoList() != null) {
            for (EasyToDo todo : category.getEasyToDoList()) {
                this.todos.add(new EasyToDoDto(todo));
            }
        }
    }
}
