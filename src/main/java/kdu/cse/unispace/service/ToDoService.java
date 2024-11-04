package kdu.cse.unispace.service;

import kdu.cse.unispace.domain.space.schedule.ToDo;
import kdu.cse.unispace.exception.schedule.ScheduleNotFoundException;
import kdu.cse.unispace.exception.todo.ToDoActiveException;
import kdu.cse.unispace.exception.todo.ToDoNotFoundException;
import kdu.cse.unispace.repository.ToDoRepository;
import kdu.cse.unispace.requestdto.schedule.todo.ChangeToDoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Transactional
    public Long makeTodo(ToDo todo) {
        ToDo saveToDo = toDoRepository.save(todo);
        return saveToDo.getId();
    }

    public ToDo findToDo(Long todoId) {
        ToDo findToDo = toDoRepository.findById(todoId).orElseThrow(()
                -> new ToDoNotFoundException("해당하는 투두가 존재하지 않습니다."));
        return findToDo;
    }

    @Transactional
    public Long updateToDo(Long id, String description, Boolean completed) {
        ToDo findToDo = findToDo(id);
        findToDo.changeDescription(description);
        findToDo.updateCompleted(completed);
        return findToDo.getId();
    }
    @Transactional
    public Long activeToDo(Long todoId) {
        ToDo toDo = findToDo(todoId);
        toDo.changeActive();
        return todoId;
    }
    @Transactional
    public void deleteToDo(Long id) {
        toDoRepository.deleteById(id);
    }

    @Transactional
    public Long changeToday(Long todoId) {
        ToDo toDo = findToDo(todoId);
        if (!toDo.isActive()) {
            throw new ToDoActiveException("활성화 중인 투두에 한해서만 오늘하기가 가능합니다.");
        }
        toDo.doToday(LocalDateTime.now());
        return todoId;
    }

    @Transactional
    public Long toDoServicechangeToDoDate(Long todoId, ChangeToDoRequestDto toDoRequestDto) {
        ToDo toDo = findToDo(todoId);
        if (!toDo.isActive()) {
            throw new ToDoActiveException("활성화중인 투두에 한해서 시간 변경이 가능합니다.");
        }
        toDo.doToday(toDoRequestDto.getChangeDay());
        return todoId;
    }

    public ToDo findTodo(Long toDoId) {
        return null;
    }
}